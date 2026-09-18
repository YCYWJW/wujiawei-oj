package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.QuestionComment;
import com.wujiawei.oj.model.entity.user.User;
import com.wujiawei.oj.model.vo.question.QuestionCommentVO;
import com.wujiawei.oj.utils.page.PageUtils;
import com.wujiawei.oj.utils.page.PageVO;

import java.util.List;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-11-13 21:54:02
 */
public interface QuestionCommentService extends IService<QuestionComment> {

    PageUtils queryPage(PageVO queryVO);

    List<QuestionCommentVO> getQuestionCommentVOs(List<?> list, User loginUser);

    boolean thumbComment(Long commentId, int opsValue);
}

