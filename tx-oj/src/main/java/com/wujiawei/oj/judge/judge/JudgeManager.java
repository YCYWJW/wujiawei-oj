package com.wujiawei.oj.judge.judge;

import com.wujiawei.oj.model.enume.LanguageEnum;
import com.wujiawei.oj.judge.JudgeInfo;
import com.wujiawei.oj.judge.judge.strategy.JudgeStrategy;
import com.wujiawei.oj.utils.SpringContextUtils;

/**
 * @author wujiawei
 * @date 2023/11/14 0:49:49
 * 注释：判题管理器 根据用户提交的代码语言 选择合适的判题策略来进行判题
 */
public class JudgeManager {
    /**
     * 判题
     * @return
     */
    public static JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeStrategy judgeStrategy = (JudgeStrategy) SpringContextUtils.getBean("javaJudgeStrategy");
        if (judgeContext.getQuestionSubmit().getLanguage()
                .equals(LanguageEnum.JAVA.getValue())) {
            judgeStrategy = (JudgeStrategy) SpringContextUtils.getBean("javaJudgeStrategy");
        }
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        return judgeInfo;
    }

}
