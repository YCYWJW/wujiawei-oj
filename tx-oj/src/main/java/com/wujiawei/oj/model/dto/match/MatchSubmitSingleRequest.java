package com.wujiawei.oj.model.dto.match;

import com.wujiawei.oj.model.dto.submit.QuestionSubmitDoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchSubmitSingleRequest extends QuestionSubmitDoRequest {
    /**
     * 竞赛id
     */
    private Long matchId;
    /**
     * 是否结束比赛
     */
    private boolean finished;
}
