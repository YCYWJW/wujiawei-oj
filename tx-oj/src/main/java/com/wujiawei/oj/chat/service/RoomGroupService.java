package com.wujiawei.oj.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.chat.RoomGroup;

import java.util.List;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 10:48:15
 */
public interface RoomGroupService extends IService<RoomGroup> {
    RoomGroup getByRoomId(Long roomId);

    List<RoomGroup> listByRoomIds(List<Long> roomIds);

//    PageUtils queryPage(Map<String, Object> params);
}

