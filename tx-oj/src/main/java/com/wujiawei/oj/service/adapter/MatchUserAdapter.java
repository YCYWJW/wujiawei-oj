package com.wujiawei.oj.service.adapter;

import com.wujiawei.oj.model.entity.match.MatchUserRelate;
import com.wujiawei.oj.model.enume.match.MatchUserJudgeStatusEnum;

import java.util.Date;

public class MatchUserAdapter {

    public static MatchUserRelate buildMatchUserRelate(Long matchId, Long userId, Integer joinType) {
        return MatchUserRelate.builder()
                .matchId(matchId)
                .userId(userId)
                .joinType(joinType)
                .acCount(-1)
                .score(0)
                .startTime(new Date())
                .gradeRank(-1)
                .judgeStatus(MatchUserJudgeStatusEnum.WAITTING.getCode())
                .unAcRateSum(-1)
                .build();
    }
}
