package com.wujiawei.oj.service;

import com.wujiawei.oj.common.PageRequest;
import com.wujiawei.oj.model.dto.forum.ForumCursorPageRequest;
import com.wujiawei.oj.model.dto.forum.TopicCommentRequest;
import com.wujiawei.oj.model.dto.forum.TopicPublishRequest;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.model.vo.forum.TopicCommentVO;
import com.wujiawei.oj.model.vo.forum.TopicVO;
import com.wujiawei.oj.utils.page.PageUtils;

import java.util.List;

public interface TopicAppService {
    Long addTopic(TopicPublishRequest request, Long userId);

    Long commentTopic(TopicCommentRequest request, Long userId);

    List<TopicCommentVO> getCommentsByTopicId(Long topicId);

    CursorPageBaseVO<TopicVO> getTopicPageByCursor(ForumCursorPageRequest pageRequest);

    void deleteComment(Long commentId, Long userId);

    PageUtils getUserFavour(Long userId, PageRequest pageRequest);

    void deleteTopicBatch(Long[] topicIds, Long userId);

    Long updateTopic(TopicPublishRequest request, Long userId);
}
