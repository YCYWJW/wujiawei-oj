package com.wujiawei.oj.controller.forum;

import cn.hutool.core.collection.CollectionUtil;
import com.wujiawei.oj.annotation.AuthCheck;
import com.wujiawei.oj.aop.AuthInterceptor;
import com.wujiawei.oj.common.PageRequest;
import com.wujiawei.oj.exception.ThrowUtils;
import com.wujiawei.oj.model.dto.forum.ForumCursorPageRequest;
import com.wujiawei.oj.model.dto.forum.TopicCommentRequest;
import com.wujiawei.oj.model.dto.forum.TopicPublishRequest;
import com.wujiawei.oj.model.dto.postfavour.PostFavourAddRequest;
import com.wujiawei.oj.model.dto.question.postthumb.PostThumbAddRequest;
import com.wujiawei.oj.model.entity.forum.Topic;
import com.wujiawei.oj.model.entity.user.User;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.model.vo.forum.TopicCommentVO;
import com.wujiawei.oj.model.vo.forum.TopicDetailVO;
import com.wujiawei.oj.model.vo.forum.TopicVO;
import com.wujiawei.oj.service.TopicAppService;
import com.wujiawei.oj.service.TopicFavourService;
import com.wujiawei.oj.service.TopicService;
import com.wujiawei.oj.service.TopicThumbService;
import com.wujiawei.oj.service.adapter.TopicAdapter;
import com.wujiawei.oj.utils.R;
import com.wujiawei.oj.utils.page.PageUtils;
import com.wujiawei.oj.utils.page.PageVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/forum/topic")
public class TopicAppController {

    @Autowired
    TopicAppService topicAppService;
    @Autowired
    TopicService topicService;
    @Autowired
    TopicAdapter topicAdapter;
    @Autowired
    TopicThumbService topicThumbService;
    @Autowired
    TopicFavourService topicFavourService;

    /**
     * 发布话题
     *
     * @param request
     * @return
     */
    @PostMapping("/publish")
    @AuthCheck(mustRole = "login")
    public R publishTopic(@RequestBody TopicPublishRequest request) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        Long topicId = topicAppService.addTopic(request, userId);
        return R.ok(topicId);
    }

    /**
     * 更新话题
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "login")
    public R updateTopic(@RequestBody TopicPublishRequest request) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        Long topicId = topicAppService.updateTopic(request, userId);
        return R.ok(topicId);
    }


    /**
     * 发布评论
     *
     * @param request
     * @return
     */
    @PostMapping("/comment")
    @AuthCheck(mustRole = "login")
    public R commentTopic(@RequestBody TopicCommentRequest request) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        Long commentId = topicAppService.commentTopic(request, userId);
        return R.ok(commentId);
    }

    /**
     * 获取评论
     *
     * @param topicId
     * @return
     */
    @GetMapping("/comment/get")
    public R getCommentsByTopicId(@RequestParam("topicId") Long topicId) {
        List<TopicCommentVO> topicCommentVOList = topicAppService.getCommentsByTopicId(topicId);
        return R.ok(topicCommentVOList);
    }

    /**
     * 分页查询 - 帖子
     *
     * @param queryVO
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody PageVO queryVO) {
        PageUtils page = topicService.queryPage(queryVO);
        if (!page.getList().isEmpty()) {
            List<TopicVO> topicVOS = topicAdapter.buildTopicVOsByTopics(page.getList());
            page.setList(topicVOS);
        }
        return R.ok().put("data", page);
    }

    /**
     * 游标分页查询
     */
    @PostMapping("/list/cursor")
    @ApiOperation("查询（游标翻页）")
    public R getTopicListByCursor(@RequestBody ForumCursorPageRequest pageRequest) {
        CursorPageBaseVO<TopicVO> cursorPageBaseVO = topicAppService.getTopicPageByCursor(pageRequest);
        return R.ok(cursorPageBaseVO);
    }

    /**
     * 获取帖子详情 用于展示
     *
     * @param id
     * @return
     */
    @GetMapping("/detail/get")
    public R getTopicById(@RequestParam("id") Long id) {
        Topic topic = topicService.getById(id);
        ThrowUtils.throwIf(topic == null, "该帖子不存在！");
        TopicVO topicVO = CollectionUtil.getFirst(topicAdapter.buildTopicVOsByTopics(Arrays.asList(topic)));
        List<TopicCommentVO> commentVOS = topicAppService.getCommentsByTopicId(id);
        TopicDetailVO build = TopicDetailVO.builder()
                .topicVO(topicVO)
                .commentVOS(commentVOS)
                .build();
        return R.ok(build);
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @PostMapping("comment/delete/{id}")
    @AuthCheck(mustRole = "login")
    public R deleteComment(@PathVariable("id") Long commentId) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        topicAppService.deleteComment(commentId, userId);
        return R.ok();
    }

    /**
     * 帖子点赞与取消
     *
     * @param postThumbAddRequest
     * @return
     */
    @PostMapping("/thumb")
    @AuthCheck(mustRole = "login")
    public R doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest) {
        // 登录才能点赞
        User loginUser = AuthInterceptor.userThreadLocal.get();
        long topicId = postThumbAddRequest.getPostId();
        int result = topicThumbService.doPostThumb(topicId, loginUser);
        return R.ok(result);
    }

    /**
     * 收藏 / 取消收藏
     */
    @PostMapping("/favour")
    @AuthCheck(mustRole = "login")
    public R doFavour(@RequestBody PostFavourAddRequest postFavourAddRequest) {
        // 登录才能操作
        final User loginUser = AuthInterceptor.userThreadLocal.get();
        long topicId = postFavourAddRequest.getPostId();
        int result = topicFavourService.doFavour(topicId, loginUser);
        return R.ok(result);
    }

    /**
     * 查出当前用户的收藏帖子
     */
    @PostMapping("/favour/user/get")
    @AuthCheck(mustRole = "login")
    public R getUserFavour(@RequestBody PageRequest pageRequest) {
        // 登录才能操作
        final User loginUser = AuthInterceptor.userThreadLocal.get();
        PageUtils pageUtils = topicAppService.getUserFavour(loginUser.getId(), pageRequest);
        return R.ok(pageUtils);
    }

    /**
     * 删除贴子
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "login")
    public R deleteTopic(@RequestBody Long[] topicIds) {
        // 登录才能操作
        final User loginUser = AuthInterceptor.userThreadLocal.get();
        topicAppService.deleteTopicBatch(topicIds, loginUser.getId());
        return R.ok();
    }

    /**
     * 获取topic基础信息
     */
    @GetMapping("/vo/get/id")
    @AuthCheck(mustRole = "login")
    public R getTopicVOById(@RequestParam("topicId") Long topicId) {
        // 登录才能操作
        final User loginUser = AuthInterceptor.userThreadLocal.get();
        Topic topic = topicService.getById(topicId);
        TopicVO topicVO = CollectionUtil.getFirst(topicAdapter.buildTopicVOsByTopics(Arrays.asList(topic)));
        return R.ok(topicVO);
    }
}
