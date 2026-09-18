package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.vo.question.QuestionVO;
import com.wujiawei.oj.model.entity.Question;
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
public interface QuestionService extends IService<Question> {

    PageUtils queryPage(PageVO queryVO);

    void validQuestion(Question question, boolean b);

    List<QuestionVO> getQuestionVOsByQuestions(List<?> list, boolean b);

    List<QuestionVO> getQuestionVOsByIds(List<Long> questions);

    List<Question> getQuestionsByRandom(Integer count);


}

