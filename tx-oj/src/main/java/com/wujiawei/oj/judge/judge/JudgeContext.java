package com.wujiawei.oj.judge.judge;

import com.wujiawei.oj.judge.JudgeInfo;
import com.wujiawei.oj.model.dto.question.JudgeCase;
import com.wujiawei.oj.model.entity.Question;
import com.wujiawei.oj.model.entity.QuestionSubmit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wujiawei
 * @date 2023/11/14 0:54:27
 * 注释：判题上下文 主要是用于存储判题过程中用到的一些信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JudgeContext {
    private QuestionSubmit questionSubmit;

    private Question question;

    private List<String> inputs;

    private List<String> outputs;

    private JudgeInfo judgeInfo;

    private String errorMsg;

    private List<JudgeCase> judgeCaseList;
    /**
     * 代码执行状态码 0 编译错误 1：通过 3：不通过
     */
    private Integer status;
}
