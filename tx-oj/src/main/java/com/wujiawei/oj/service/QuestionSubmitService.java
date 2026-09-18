package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.dto.submit.QuestionSubmitDoRequest;
import com.wujiawei.oj.model.entity.QuestionSubmit;
import com.wujiawei.oj.model.vo.question.ChartDataVO;
import com.wujiawei.oj.model.vo.question.QuestionSubmitSimpleVO;
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
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    PageUtils queryPage(PageVO queryVO);

    Long doSubmit(QuestionSubmitDoRequest questionSubmit);

    List<QuestionSubmitSimpleVO> getQuestionSubmitSimpleVOs(List<?> list);

    ChartDataVO getChartData(Long userId);
}

