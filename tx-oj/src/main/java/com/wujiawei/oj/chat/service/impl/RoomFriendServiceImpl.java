package com.wujiawei.oj.chat.service.impl;

import com.wujiawei.oj.chat.enume.RoomStatusEnum;
import com.wujiawei.oj.chat.service.RoomFriendService;
import com.wujiawei.oj.chat.service.adapter.ChatAdapter;
import com.wujiawei.oj.model.entity.chat.RoomFriend;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiawei.oj.chat.mapper.RoomFriendMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("friendService")
public class RoomFriendServiceImpl extends ServiceImpl<RoomFriendMapper, RoomFriend> implements RoomFriendService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<FriendEntity> page = this.page(
//                new Query<FriendEntity>().getPage(params),
//                new QueryWrapper<FriendEntity>()
//        );
//
//        return new PageUtils(page);
//    }

    /**
     * @param userId1
     * @param userId2
     * @return
     */
    @Override
    public RoomFriend getByUserIds(Long userId1, Long userId2) {
        return lambdaQuery().eq(RoomFriend::getUserId1, userId1)
                .eq(RoomFriend::getUserId2, userId2).one();
    }

    /**
     * 创建RoomFriend
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomFriend createRoomFriend(Long roomId, Long userId1, Long userId2) {
        RoomFriend roomFriend = ChatAdapter.buildRoomFriend(roomId, userId1, userId2);
        this.save(roomFriend);
        return roomFriend;
    }

    @Override
    public RoomFriend getByRoomId(Long roomId) {
        return lambdaQuery()
                .eq(RoomFriend::getRoomId, roomId)
                .one();
    }

    @Override
    public List<RoomFriend> listByRoomIds(List<Long> roomIds) {
        return lambdaQuery().in(RoomFriend::getRoomId, roomIds).list();
    }

    @Override
    public void disableRoom(List<Long> userIdList) {
        lambdaUpdate()
                .eq(RoomFriend::getUserId1, userIdList.get(0))
                .eq(RoomFriend::getUserId2, userIdList.get(1))
                .set(RoomFriend::getStatus, RoomStatusEnum.FORBIDDEN.getCode())
                .update();
    }
}
