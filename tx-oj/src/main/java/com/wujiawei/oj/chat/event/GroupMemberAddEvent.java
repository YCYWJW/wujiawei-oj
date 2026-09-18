package com.wujiawei.oj.chat.event;

import com.wujiawei.oj.model.entity.chat.GroupMember;
import com.wujiawei.oj.model.entity.chat.RoomGroup;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author wujiawei
 * @date 2024/1/2 12:59:02
 * 注释：
 */
@Getter
public class GroupMemberAddEvent extends ApplicationEvent {

    private final List<GroupMember> memberList;

    private final RoomGroup roomGroup;

    private final Long inviteUserId;

    public GroupMemberAddEvent(Object source, RoomGroup roomGroup, List<GroupMember> memberList, Long inviteUserId) {
        super(source);
        this.memberList = memberList;
        this.roomGroup = roomGroup;
        this.inviteUserId = inviteUserId;
    }
}
