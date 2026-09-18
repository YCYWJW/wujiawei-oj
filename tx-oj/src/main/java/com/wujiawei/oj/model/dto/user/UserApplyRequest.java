package com.wujiawei.oj.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wujiawei
 * @date 2023/12/29 9:10:21
 * 注释：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserApplyRequest {
    /**
     * 申请信息
     */
    private String msg;
    /**
     * 目标id(接收人id)
     */
    private Long targetUserId;
}
