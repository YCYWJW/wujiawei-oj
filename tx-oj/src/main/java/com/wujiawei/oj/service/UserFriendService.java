package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.entity.user.UserFriend;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.model.vo.user.FriendVO;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 23:46:51
 */
public interface UserFriendService extends IService<UserFriend> {
    UserFriend getByFriend(Long userId, Long targetUserId);

    void createFriendRelate(Long userId, Long targetId);

    CursorPageBaseVO<FriendVO> cursorPage(Long userId, CursorPageBaseRequest cursorPageBaseRequest);

    CursorPageBaseVO<UserFriend> getFriendPage(Long userId, CursorPageBaseRequest cursorPageBaseRequest);

    void deleteFriend(Long userId, Long friendId);

//    PageUtils queryPage(Map<String, Object> params);
}

