package com.wujiawei.oj.chat.enume;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wujiawei
 * @date 2023/12/29 15:32:08
 * 注释：
 */
@Getter
@AllArgsConstructor
public enum RoomTypeEnum {
    FRIEND(0, "私聊"),

    GROUP(1, "群聊");

    private final Integer code;
    private final String msg;
}
