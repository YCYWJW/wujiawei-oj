package com.wujiawei.oj.chat.controller;

import com.wujiawei.oj.annotation.AuthCheck;
import com.wujiawei.oj.aop.AuthInterceptor;
import com.wujiawei.oj.chat.domain.vo.request.ChatMessageRequest;
import com.wujiawei.oj.chat.domain.vo.request.MessagePageRequest;
import com.wujiawei.oj.chat.domain.vo.response.ChatMessageVO;
import com.wujiawei.oj.chat.service.MessageService;
import com.wujiawei.oj.chat.service.business.ChatService;
import com.wujiawei.oj.model.vo.cursor.CursorPageBaseVO;
import com.wujiawei.oj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 10:48:15
 */
@RestController
@RequestMapping("chat/message")
@Api(tags = "聊天-消息相关接口")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;

    @PostMapping("/msg")
    @ApiOperation("发送消息")
    @AuthCheck(mustRole = "login")
    public R sendMsg(@RequestBody ChatMessageRequest request) {
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        Long msgId = chatService.sendMsg(request, userId);
        ChatMessageVO chatMessageVO = chatService.getMessageVO(msgId, userId);
        return R.ok(chatMessageVO);
    }

    /**
     * 分页查询（游标翻页）
     */
    @PostMapping("/list")
    @ApiOperation("查询（游标翻页）")
    @AuthCheck(mustRole = "login")
    public R getMsgListByCursor(@RequestBody MessagePageRequest pageRequest){
        Long userId = AuthInterceptor.userThreadLocal.get().getId();
        CursorPageBaseVO<ChatMessageVO> cursorPageBaseVO = chatService.getMsgPageByCursor(pageRequest, userId);
        return R.ok(cursorPageBaseVO);
    }


    /**
     * 信息
     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Long id){
//		Message message = messageService.getById(id);
//
//        return R.ok().put("message", message);
//    }

    /**
     * 保存
     */
//    @RequestMapping("/save")
//    public R save(@RequestBody Message message){
//		messageService.save(message);
//
//        return R.ok();
//    }

    /**
     * 修改
     */
//    @RequestMapping("/update")
//    public R update(@RequestBody Message message){
//		messageService.updateById(message);
//
//        return R.ok();
//    }

    /**
     * 删除
     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Long[] ids){
//		messageService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}
