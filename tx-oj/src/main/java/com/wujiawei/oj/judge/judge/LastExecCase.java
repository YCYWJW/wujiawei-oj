package com.wujiawei.oj.judge.judge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wujiawei
 * @date 2024/4/11 13:23
 * 注释：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LastExecCase {
    public String input;

    public String output;

    public String actualOutput;
}
