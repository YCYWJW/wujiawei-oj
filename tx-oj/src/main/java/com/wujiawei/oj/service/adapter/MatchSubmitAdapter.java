package com.wujiawei.oj.service.adapter;

import com.wujiawei.oj.model.dto.submit.QuestionSubmitDoRequest;
import com.wujiawei.oj.model.entity.QuestionSubmit;
import com.wujiawei.oj.model.enume.JudgeStatusEnum;

public class MatchSubmitAdapter {
    public static QuestionSubmit buildQuestionSubmit(QuestionSubmitDoRequest request, Long userId) {
        QuestionSubmit submit = QuestionSubmit.builder()
                .questionId(request.getQuestionId())
                .code(request.getCode())
                .language(request.getLanguage())
                .status(JudgeStatusEnum.WAITTING.getValue())
                .userId(userId)
                .exceedPercent(-1f)
                .judgeInfo("{}")
                .build();
        return submit;
    }
}
