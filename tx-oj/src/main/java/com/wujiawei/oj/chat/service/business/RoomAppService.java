package com.wujiawei.oj.chat.service.business;

import com.wujiawei.oj.chat.domain.vo.request.GroupAddRequest;
import com.wujiawei.oj.chat.domain.vo.request.GroupMemberRemoveRequest;
import com.wujiawei.oj.chat.domain.vo.request.GroupMemberRequest;
import com.wujiawei.oj.chat.domain.vo.response.ChatMemberVO;
import com.wujiawei.oj.chat.domain.vo.response.ChatRoomVO;
import com.wujiawei.oj.chat.domain.vo.response.GroupDetailVO;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;

import java.util.List;

/**
 * @author wujiawei
 * @date 2023/12/31 14:50:25
 * 注释：
 */
public interface RoomAppService {
    CursorPageBaseVO<ChatRoomVO> getContactPageByCursor(CursorPageBaseRequest cursorPageBaseRequest, Long userId);

    List<ChatRoomVO> buildContactResp(Long userId, List<Long> roomIds);

    ChatRoomVO getContactDetailByRoomId(Long roomId, Long userId);

    ChatRoomVO getContactDetailByFriendId(Long userId, Long friendId);

    GroupDetailVO getGroupDetail(Long userId, Long roomId);

    CursorPageBaseVO<ChatMemberVO> getGroupMembersByCursor(GroupMemberRequest groupMemberRequest);

    Long addGroup(GroupAddRequest groupAddRequest, Long userId);

    void deleteFriendRoom(List<Long> asList);

    Long disableRoom(List<Long> asList);

    void removeGroupMember(GroupMemberRemoveRequest groupMemberRemoveRequest);
}
