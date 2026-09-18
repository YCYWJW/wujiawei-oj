package com.wujiawei.oj.judge.judge.strategy;

import com.wujiawei.oj.judge.JudgeInfo;
import com.wujiawei.oj.judge.judge.JudgeContext;

/**
 * @author wujiawei
 * @date 2023/11/14 0:59:38
 * 注释：判题策略接口
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
