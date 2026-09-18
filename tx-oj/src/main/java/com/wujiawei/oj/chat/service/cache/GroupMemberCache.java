package com.wujiawei.oj.chat.service.cache;

import com.wujiawei.oj.chat.service.GroupMemberService;
import com.wujiawei.oj.chat.service.RoomGroupService;
import com.wujiawei.oj.model.entity.chat.RoomGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author wujiawei
 * @date 2023/12/30 22:34:32
 * 注释：
 */
@Component
public class GroupMemberCache {

    @Autowired
    RoomGroupService roomGroupService;
    @Autowired
    GroupMemberService groupMemberService;

    @Cacheable(cacheNames = "oj:chat:group", key = "'groupMember'+#roomId")
    public List<Long> getMemberUserIdList(Long roomId) {
        RoomGroup roomGroup = roomGroupService.getByRoomId(roomId);
        if (Objects.isNull(roomGroup)) {
            return null;
        }
        return groupMemberService.getMemberListByGroupId(roomGroup.getId());
    }

    @CacheEvict(cacheNames = "oj:chat:group", key = "'groupMember'+#roomId")
    public List<Long> evictMemberIdList(Long roomId) {
        return null;
    }
}
