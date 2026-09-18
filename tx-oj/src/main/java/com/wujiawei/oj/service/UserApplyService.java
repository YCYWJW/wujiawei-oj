package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.chat.domain.vo.response.ChatMessageVO;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.dto.user.UserApplyRequest;
import com.wujiawei.oj.model.entity.user.UserApply;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.model.vo.user.FriendApplyVO;

import java.util.Date;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 23:46:51
 */
public interface UserApplyService extends IService<UserApply> {
    void applyFriend(Long userId, UserApplyRequest request);

    ChatMessageVO agreeApply(Long userId, Long applyId);

    CursorPageBaseVO<FriendApplyVO> getPageByCursor(CursorPageBaseRequest cursorPageBaseRequest, Long id);

    Integer getUnReadApplyCount(Long targetId);

    void markReadFriendApply(Long userId, Date date);

//    PageUtils queryPage(Map<String, Object> params);
}

