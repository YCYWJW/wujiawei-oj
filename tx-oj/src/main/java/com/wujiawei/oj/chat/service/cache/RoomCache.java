package com.wujiawei.oj.chat.service.cache;

import com.wujiawei.oj.chat.service.RoomService;
import com.wujiawei.oj.constant.RedisKeyConstant;
import com.wujiawei.oj.model.entity.chat.Room;
import com.wujiawei.oj.service.cache.AbstractRedisStringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wujiawei
 * @date 2023/12/29 21:11:25
 * 注释：
 */
@Component
public class RoomCache extends AbstractRedisStringCache<Long, Room> {

    @Autowired
    RoomService roomService;

    /**
     * 获取key
     * @param roomId
     * @return
     */
    @Override
    protected String getKey(Long roomId) {
        return RedisKeyConstant.getKey(RedisKeyConstant.ROOM_INFO, roomId);
    }

    @Override
    protected Long getExpireSeconds() {
        return 5 * 60L;
    }

    /**
     *
     * @param roomIds
     * @return
     */
    @Override
    protected Map<Long, Room> load(List<Long> roomIds) {
        List<Room> rooms = roomService.listByIds(roomIds);
        return rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
    }
}
