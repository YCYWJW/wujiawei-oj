package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.QuestionFavour;
import com.wujiawei.oj.utils.page.PageUtils;
import com.wujiawei.oj.utils.page.PageVO;

/**
 * 
 *
 * @author wujiawei
 * @email 
 * @date 2023-11-20 19:18:19
 */
public interface QuestionFavourService extends IService<QuestionFavour> {

    PageUtils queryPage(PageVO params);

    Boolean favourQuestion(Long questionId);
}

