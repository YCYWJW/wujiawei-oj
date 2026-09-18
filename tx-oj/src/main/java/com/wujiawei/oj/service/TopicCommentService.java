package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.forum.TopicComment;

import java.util.List;

/**
 * 
 *
 * @author wujiawei
 * @email 
 * @date 2024-03-27 12:59:56
 */
public interface TopicCommentService extends IService<TopicComment> {
    List<TopicComment> getCommentsByTopicId(Long topicId);

//    PageUtils queryPage(Map<String, Object> params);
}

