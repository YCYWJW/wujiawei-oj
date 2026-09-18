package com.wujiawei.oj.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.entity.chat.Message;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;

import java.util.Date;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 10:48:15
 */
public interface MessageService extends IService<Message> {
    CursorPageBaseVO<Message> getPageByCursor(Long roomId, CursorPageBaseRequest pageRequest, Date lastMsgTime);

    Integer getUnReadCount(Long roomId, Date readTime);

//    PageUtils queryPage(Map<String, Object> params);
}

