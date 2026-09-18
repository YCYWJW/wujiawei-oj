package com.wujiawei.oj.chat.service.impl;

import com.wujiawei.oj.chat.mapper.MessageMapper;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.entity.chat.Message;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.utils.CursorUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiawei.oj.chat.service.MessageService;


@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<MessageEntity> page = this.page(
//                new Query<MessageEntity>().getPage(params),
//                new QueryWrapper<MessageEntity>()
//        );
//
//        return new PageUtils(page);
//    }


    /**
     * 游标翻页
     *
     * @param roomId
     * @param pageRequest
     * @param lastMsgTime
     * @return
     */
    @Override
    public CursorPageBaseVO<Message> getPageByCursor(Long roomId, CursorPageBaseRequest pageRequest, Date lastMsgTime) {
        CursorPageBaseVO<Message> cursorPageByMysql = CursorUtils.getCursorPageByMysql(this, pageRequest, wrapper -> {
            wrapper.eq(Message::getRoomId, roomId);
            wrapper.le(lastMsgTime != null, Message::getCreateTime, lastMsgTime);
        }, Message::getCreateTime);
        return cursorPageByMysql;
    }

    /**
     * 获取未读消息数目
     *
     * @param roomId
     * @param readTime
     * @return
     */
    @Override
    public Integer getUnReadCount(Long roomId, Date readTime) {
        return lambdaQuery().eq(Message::getRoomId, roomId)
                .gt(readTime != null, Message::getCreateTime, readTime)
                .count();
    }
}
