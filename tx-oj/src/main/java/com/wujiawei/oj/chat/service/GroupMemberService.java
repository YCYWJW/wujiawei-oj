package com.wujiawei.oj.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.entity.chat.GroupMember;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;

import java.util.List;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 10:48:15
 */
public interface GroupMemberService extends IService<GroupMember> {
    List<Long> getMemberListByGroupId(Long id);

    GroupMember getMember(Long userId, Long groupId);

    CursorPageBaseVO<GroupMember> getMembersPageByCursor(CursorPageBaseRequest request, Long groupId);

    void dissolveGroup(Long groupId, Long userId);

//    PageUtils queryPage(Map<String, Object> params);
}

