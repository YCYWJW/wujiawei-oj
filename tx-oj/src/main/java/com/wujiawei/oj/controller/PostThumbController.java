package com.wujiawei.oj.controller;

import com.wujiawei.oj.annotation.AuthCheck;
import com.wujiawei.oj.aop.AuthInterceptor;
import com.wujiawei.oj.exception.BusinessException;
import com.wujiawei.oj.model.dto.question.postthumb.PostThumbAddRequest;
import com.wujiawei.oj.model.entity.user.User;
import com.wujiawei.oj.model.enume.TxCodeEnume;
import com.wujiawei.oj.service.PostThumbService;
import com.wujiawei.oj.service.UserService;
import com.wujiawei.oj.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子点赞接口
 *
 * @author wujiawei
 * @date 2023/1/24 3:44:13
 * 注释：
 */
@RestController
@RequestMapping("/post_thumb")
@Slf4j
public class PostThumbController {

    @Resource
    private PostThumbService postThumbService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param postThumbAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    @AuthCheck(mustRole = "login")
    public R doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
            HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(TxCodeEnume.COMMON_SUBMIT_DATA_EXCEPTION);
        }
        // 登录才能点赞
//        final User loginUser = userService.getLoginUser(request);
        User loginUser = AuthInterceptor.userThreadLocal.get();
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return R.ok(result);
    }

}
