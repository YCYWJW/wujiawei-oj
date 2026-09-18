package com.wujiawei.oj.chat.controller;

import com.wujiawei.oj.annotation.AuthCheck;
import com.wujiawei.oj.aop.AuthInterceptor;
import com.wujiawei.oj.chat.domain.vo.request.ContactUpdateOrAddRequest;
import com.wujiawei.oj.chat.domain.vo.request.RemoveSessionRequest;
import com.wujiawei.oj.chat.domain.vo.response.ChatRoomVO;
import com.wujiawei.oj.chat.enume.RoomStatusEnum;
import com.wujiawei.oj.chat.service.ContactService;
import com.wujiawei.oj.chat.service.RoomFriendService;
import com.wujiawei.oj.chat.service.RoomService;
import com.wujiawei.oj.chat.service.business.ChatService;
import com.wujiawei.oj.chat.service.business.RoomAppService;
import com.wujiawei.oj.chat.service.cache.RoomCache;
import com.wujiawei.oj.model.dto.cursor.CursorPageBaseRequest;
import com.wujiawei.oj.model.entity.chat.Room;
import com.wujiawei.oj.model.entity.chat.RoomFriend;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author wujiawei
 * @email
 * @date 2023-12-28 10:48:15
 */
@RestController
@Api(tags = "聊天会话相关接口")
@RequestMapping("chat/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    RoomService roomService;
    @Autowired
    RoomAppService roomAppService;
    @Autowired
    ChatService chatService;
    @Autowired
    RoomFriendService roomFriendService;
    @Autowired
    RoomCache roomCache;

    /**
     * 会话分页查询（游标翻页）
     */
    @PostMapping("/list")
    @ApiOperation("查询（游标翻页）")
    @AuthCheck(mustRole = "login")
    public R getSessionPageByCursor(@RequestBody CursorPageBaseRequest cursorPageBaseRequest) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        CursorPageBaseVO<ChatRoomVO> cursorPageBaseVO = roomAppService.getContactPageByCursor(cursorPageBaseRequest, userId);
        return R.ok(cursorPageBaseVO);
    }


    @GetMapping("detail/get")
    @ApiOperation("获取会话详情")
    @AuthCheck(mustRole = "login")
    public R getDetailByRoomId(@RequestParam("roomId") Long roomId) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        ChatRoomVO chatRoomVO = roomAppService.getContactDetailByRoomId(roomId, userId);
        return R.ok(chatRoomVO);
    }

    @GetMapping("detail/friend/get")
    @ApiOperation("获取会话详情（by FriendId）")
    @AuthCheck(mustRole = "login")
    public R getDetailByFriendId(@RequestParam("friendId") Long friendId) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        ChatRoomVO chatRoomVO = roomAppService.getContactDetailByFriendId(userId, friendId);
        return R.ok(chatRoomVO);
    }


    @PostMapping("/msg/read")
    @ApiOperation("消息阅读上报")
    @AuthCheck(mustRole = "login")
    public R msgReadReport(@RequestParam("roomId") @ApiParam("房间id") Long roomId) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        chatService.msgRead(userId, roomId);
        return R.ok();
    }

    /**
     * 移除会话
     */
    @PostMapping("remove")
    @AuthCheck(mustRole = "login")
    @ApiOperation("移除会话")
    public R removeSession(@RequestBody RemoveSessionRequest removeSessionRequest) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        contactService.removeSession(userId, removeSessionRequest);
        return R.ok();
    }

    @PostMapping("/add")
    @AuthCheck(mustRole = "login")
    @ApiOperation("更新（活跃时间）/创建会话")
    public R updateOrCreateContact(@RequestBody ContactUpdateOrAddRequest updateOrAddRequest) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        if (updateOrAddRequest.getRoomId() == null) {
            RoomFriend roomFriend = roomFriendService.getByUserIds(userId, updateOrAddRequest.getFriendId());
            updateOrAddRequest.setRoomId(roomFriend.getRoomId());
        }
        // 检查 Room是否可用
        Room room = roomCache.get(updateOrAddRequest.getRoomId());
        if (RoomStatusEnum.FORBIDDEN.getCode().equals(room.getStatus())) {
            return R.error("当前不是好友");
        }
        contactService.updateOrCreateContact(userId, updateOrAddRequest);
        ChatRoomVO chatRoomVO = roomAppService.buildContactResp(userId, Arrays.asList(updateOrAddRequest.getRoomId())).get(0);
        return R.ok(chatRoomVO);
    }


    /**
     * 信息
     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Long id) {
//        Contact contact = contactService.getById(id);
//
//        return R.ok().put("contact", contact);
//    }

    /**
     * 保存
     */
//    @RequestMapping("/save")
//    public R save(@RequestBody Contact contact) {
//        contactService.save(contact);
//
//        return R.ok();
//    }

    /**
     * 修改
     */
//    @RequestMapping("/update")
//    public R update(@RequestBody Contact contact) {
//        contactService.updateById(contact);
//
//        return R.ok();
//    }

    /**
     * 删除
     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] ids) {
//        contactService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}
