package com.wujiawei.oj.listener;

import com.wujiawei.oj.judge.JudgeService;
import com.wujiawei.oj.model.entity.QuestionSubmit;
import com.wujiawei.oj.model.enume.JudgeStatusEnum;
import com.wujiawei.oj.service.QuestionSubmitService;
import com.wujiawei.oj.utils.RedisUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author wujiawei
 * @date 2023/12/6 17:06:15
 * 注释：
 */
@Service
//@RabbitListener(queues = {"waitting.judge.queue", "match.handle.queue"})
@Slf4j
public class JudgeMqListener {

    /**
     * 同一条提交最多允许重试的次数，超过则丢弃消息并标记为「系统异常」
     */
    private static final int MAX_RETRY = 3;

    /**
     * 判题重试次数的 Redis key 前缀，完整 key 形如 judge:retry:123456789
     */
    private static final String RETRY_KEY_PREFIX = "judge:retry:";

    @Autowired
    JudgeService judgeService;

    @Autowired
    QuestionSubmitService questionSubmitService;

    /**
     * 该监听器负责处理队列里的判题请求
     *
     * @param submitId
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitListener(queues = "waitting.judge.queue.prod")
    @RabbitHandler
    public void listener(Long submitId, Channel channel, Message message) throws IOException {
        log.info("收到提交请求： {}, 开始执行判题", submitId);
        try {
            // 开始执行请求
            judgeService.doJudge(submitId);
            log.info("完成判题，确认消息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 默认「可重试」；只要计数这一环出问题，就改成直接丢弃，避免消息在队列里无限打转
            boolean requeue = true;
            try {
                // 累计失败次数，key 1 小时后自动过期（Redis 挂了会抛异常，走下面的兜底分支）
                Long retry = RedisUtils.inc(RETRY_KEY_PREFIX + submitId, 1, TimeUnit.HOURS);
                int retryCount = retry == null ? 1 : retry.intValue();
                if (retryCount > MAX_RETRY) {
                    // 超过上限：不再重试，先标记为「系统异常」，再丢弃消息
                    requeue = false;
                    log.error("判题连续失败 {} 次，丢弃消息并标记为系统异常，submitId：{}", retryCount, submitId, e);
                    markSystemError(submitId);
                } else {
                    log.error("判题出错，第 {} 次重试，submitId：{}", retryCount, submitId, e);
                }
            } catch (Exception guardException) {
                // 计数或标记状态本身出错：也绝不能让它把消息无限重投，直接丢弃
                requeue = false;
                log.error("判题重试计数异常，改为直接丢弃消息，submitId：{}", submitId, guardException);
            }
            // 第二个参数 false = 不再重新入队（丢弃）；true = 退回队列等待重试
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), requeue);
        }
    }

    /**
     * 把该条提交标记为「系统异常」，避免状态永远停在「等待中」
     */
    private void markSystemError(Long submitId) {
        QuestionSubmit update = new QuestionSubmit();
        update.setId(submitId);
        update.setStatus(JudgeStatusEnum.EMIT_MQ_FAILURE.getValue());
        questionSubmitService.updateById(update);
    }

}
