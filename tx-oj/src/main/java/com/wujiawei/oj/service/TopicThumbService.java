package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.forum.TopicThumb;
import com.wujiawei.oj.model.entity.user.User;

/**
 * @author wujiawei
 * @email
 * @date 2024-03-27 12:59:56
 */
public interface TopicThumbService extends IService<TopicThumb> {
    int doPostThumb(long topicId, User loginUser);

    int doPostThumbInner(long userId, long topicId);

//    PageUtils queryPage(Map<String, Object> params);
}

