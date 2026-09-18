package com.wujiawei.oj.model.enume;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wujiawei
 * @date 2023/12/29 9:58:32
 * 注释：
 */
@Getter
@AllArgsConstructor
public enum UserApplyTypeEnum {

    ADD_FRIEND(0, "加好友");

    private final Integer code;

    private final String msg;
}
