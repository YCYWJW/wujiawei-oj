package com.wujiawei.oj.chat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wujiawei
 * @date 2024/1/2 21:22:01
 * 注释：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WsChannelExtraDTO {
    /**
     * 用户id
     */
    private Long userId;
}
