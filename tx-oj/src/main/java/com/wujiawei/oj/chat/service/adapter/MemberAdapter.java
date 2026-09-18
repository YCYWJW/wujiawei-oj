package com.wujiawei.oj.chat.service.adapter;

import com.wujiawei.oj.chat.domain.vo.response.ChatMemberVO;
import com.wujiawei.oj.chat.domain.vo.response.WsBaseVO;
import com.wujiawei.oj.chat.domain.vo.response.WsGroupMemberChangeVO;
import com.wujiawei.oj.chat.enume.GroupRoleEnum;
import com.wujiawei.oj.chat.enume.WsRespTypeEnum;
import com.wujiawei.oj.model.entity.chat.GroupMember;
import com.wujiawei.oj.model.entity.user.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wujiawei
 * @date 2024/1/1 21:33:34
 * 注释：
 */
public class MemberAdapter {
    public static List<ChatMemberVO> buildChatMember(List<User> userList) {
        List<ChatMemberVO> collect = userList.stream().map(user -> {
            ChatMemberVO chatMemberVO = new ChatMemberVO();
            chatMemberVO.setUserId(user.getId());
            chatMemberVO.setActiveStatus(user.getActiveStatus());
            chatMemberVO.setLastOpsTime(user.getLastOpsTime());
            return chatMemberVO;
        }).collect(Collectors.toList());
        return collect;
    }

    public static List<GroupMember> buildGroupMemberBatch(List<Long> userIdList, Long groupId) {
        return userIdList.stream().map(userId -> {
            return GroupMember.builder()
                    .role(GroupRoleEnum.MEMBER.getType())
                    .groupId(groupId)
                    .userId(userId)
                    .build();
        }).collect(Collectors.toList());
    }

    public static WsBaseVO<WsGroupMemberChangeVO> buildGroupMemberAddWs(Long roomId, User user) {
        WsGroupMemberChangeVO memberChangeVO = WsGroupMemberChangeVO.builder()
                .userId(user.getId())
                .activeStatus(user.getActiveStatus())
                .lastOpsTime(user.getLastOpsTime())
                .roomId(roomId)
                .changeType(WsGroupMemberChangeVO.CHANGE_TYPE_ADD)
                .build();
        WsBaseVO<WsGroupMemberChangeVO> wsBaseVO = new WsBaseVO<>();
        wsBaseVO.setType(WsRespTypeEnum.GROUP_MEMBER_CHANGE.getType());
        wsBaseVO.setData(memberChangeVO);
        return wsBaseVO;
    }
}
