package com.wujiawei.oj.chat.enume;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wujiawei
 * @date 2023/12/29 16:28:55
 * 注释：
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
    TEXT(0, "文本"),
    RECALL(1, "撤回消息"),
    IMG(2, "图片"),
    SYSTEM(3, "系统消息");

    private final Integer code;
    private final String msg;
}
