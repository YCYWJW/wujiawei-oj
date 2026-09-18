package com.wujiawei.oj.chat.service.business;

import com.wujiawei.oj.chat.domain.dto.WsAuthorize;
import com.wujiawei.oj.chat.domain.vo.response.WsBaseVO;
import io.netty.channel.Channel;

/**
 * @author wujiawei
 * @date 2024/1/2 19:17:54
 * 注释：
 */
public interface WebSocketService {
    void removed(Channel channel);

    void sendToAllOnline(WsBaseVO<?> buildOffLineNotifyWsVO, Long userId);

    void connect(Channel channel);

    void authorize(Channel channel, WsAuthorize wsAuthorize);

    void sendToUserId(WsBaseVO<?> wsBaseVO, Long userId);
}
