package com.wujiawei.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiawei.oj.mapper.TopicCommentMapper;
import com.wujiawei.oj.model.entity.forum.TopicComment;
import com.wujiawei.oj.service.TopicCommentService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("topicCommentService")
public class TopicCommentServiceImpl extends ServiceImpl<TopicCommentMapper, TopicComment> implements TopicCommentService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<TopicCommentEntity> page = this.page(
//                new Query<TopicCommentEntity>().getPage(params),
//                new QueryWrapper<TopicCommentEntity>()
//        );
//
//        return new PageUtils(page);
//    }


    @Override
    public List<TopicComment> getCommentsByTopicId(Long topicId) {
        QueryWrapper<TopicComment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(TopicComment::getTopicId, topicId);
        return this.list(wrapper);
    }
}