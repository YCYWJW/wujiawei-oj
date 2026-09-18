package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.entity.forum.Topic;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.utils.page.PageUtils;
import com.wujiawei.oj.utils.page.PageVO;

/**
 * 
 *
 * @author wujiawei
 * @email 
 * @date 2024-03-27 12:59:55
 */
public interface TopicService extends IService<Topic> {
    PageUtils queryPage(PageVO queryVO);

    CursorPageBaseVO<Topic> getTopicPageByCursor(CursorPageBaseRequest pageRequest, String keyWord);

//    PageUtils queryPage(Map<String, Object> params);
}

