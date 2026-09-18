package com.wujiawei.oj.chat.domain.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wujiawei
 * @date 2023/12/29 16:21:14
 * 注释：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {

    /**
     * 房间id
     */
    private Long roomId;
    /**
     * 消息类型
     */
    private Integer msgType;
    /**
     * 消息内容
     */
    private Object body;
}
