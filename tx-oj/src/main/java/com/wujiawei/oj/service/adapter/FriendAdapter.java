package com.wujiawei.oj.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.wujiawei.oj.chat.domain.vo.response.WsFriendApplyAgreeVO;
import com.wujiawei.oj.model.dto.user.UserApplyRequest;
import com.wujiawei.oj.model.entity.user.User;
import com.wujiawei.oj.model.entity.user.UserApply;
import com.wujiawei.oj.model.entity.user.UserFriend;
import com.wujiawei.oj.model.enume.UserApplyReadStatusEnum;
import com.wujiawei.oj.model.enume.UserApplyStatusEnum;
import com.wujiawei.oj.model.enume.UserApplyTypeEnum;
import com.wujiawei.oj.model.vo.user.FriendApplyVO;
import com.wujiawei.oj.model.vo.user.FriendVO;
import com.wujiawei.oj.model.vo.user.WsFriendApplyVO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wujiawei
 * @date 2023/12/29 9:51:24
 * 注释：好友适配器
 */
public class FriendAdapter {

    /**
     * 构建userApply
     *
     * @param userId
     * @param request
     * @return
     */
    public static UserApply buildFriendApply(Long userId, UserApplyRequest request) {
        UserApply userApply = new UserApply();
        userApply.setUserId(userId);
        userApply.setMsg(request.getMsg());
        userApply.setTargetId(request.getTargetUserId());
        userApply.setStatus(UserApplyStatusEnum.WAITTING.getCode());
        userApply.setReadStatus(UserApplyReadStatusEnum.UNREAD.getCode());
        userApply.setType(UserApplyTypeEnum.ADD_FRIEND.getCode());
        return userApply;
    }

    /**
     * @param list
     * @param userList
     * @return
     */
    public static List<FriendVO> buildFriend(List<UserFriend> list, List<User> userList) {
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        return list.stream().map((friend) -> {
            FriendVO friendVO = new FriendVO();
            friendVO.setUserId(friend.getFriendId());
            User user = userMap.get(friend.getFriendId());
            if (user != null) {
                friendVO.setActiveStatus(user.getActiveStatus());
            }
            return friendVO;
        }).collect(Collectors.toList());
    }

    public static List<FriendApplyVO> buildFriendApplyVOBatch(List<UserApply> userApplyList) {
        List<FriendApplyVO> collect = userApplyList.stream().map(userApply -> {
            FriendApplyVO friendApplyVO = new FriendApplyVO();
            BeanUtil.copyProperties(userApply, friendApplyVO);
            return friendApplyVO;
        }).collect(Collectors.toList());
        return collect;
    }

    public static WsFriendApplyVO buildWsUserApplyVO(UserApply userApply, Integer unReadCount) {
        FriendApplyVO friendApplyVO = buildFriendApplyVOBatch(Collections.singletonList(userApply)).get(0);
        WsFriendApplyVO wsFriendApplyVO = WsFriendApplyVO.builder()
                .friendApplyVO(friendApplyVO)
                .unreadCount(unReadCount)
                .build();
        return wsFriendApplyVO;
    }

    public static WsFriendApplyAgreeVO buildWsFriendApplyAgreeVO(Long userId, Integer activeStatus) {
        return WsFriendApplyAgreeVO.builder()
                .userId(userId)
                .activeStatus(activeStatus)
                .build();
    }
}
