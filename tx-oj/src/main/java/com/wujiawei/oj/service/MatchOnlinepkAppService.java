package com.wujiawei.oj.service;

import com.wujiawei.oj.common.PageRequest;
import com.wujiawei.oj.judge.JudgeInfo;
import com.wujiawei.oj.model.dto.match.MatchSubmitSingleRequest;
import com.wujiawei.oj.model.dto.question.JudgeConfig;
import com.wujiawei.oj.model.dto.submit.QuestionSubmitDoRequest;
import com.wujiawei.oj.model.entity.QuestionSubmit;
import com.wujiawei.oj.model.entity.match.OnlinePkMatch;
import com.wujiawei.oj.model.vo.match.OnlinePKResultVO;
import com.wujiawei.oj.model.vo.match.PkMatchStartVO;
import com.wujiawei.oj.utils.page.PageUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MatchOnlinepkAppService {
    Long findOpponent();

    Long submit(MatchSubmitSingleRequest request);

    boolean checkAndBuildPkMatchResult(Long matchId);

    Double computeSubmitScore(JudgeInfo judgeInfo, QuestionSubmit questionSubmit,
                              JudgeConfig judgeConfig, Long useSeconds, Long totalSeconds);

    @Transactional(rollbackFor = Exception.class)
    Long saveOrUpdateSubmit(QuestionSubmitDoRequest request, Long matchId, Long userId);

    OnlinePKResultVO getPkResult(Long matchId);

    OnlinePKResultVO buildOnlinePKResultVO(OnlinePkMatch onlinePkMatch);

    List<OnlinePKResultVO> getPkRecords(Long userId);

    PkMatchStartVO startPk(Long matchId);

    boolean cancelMatch(Long userId);

    PageUtils getPkRecordByUser(PageRequest pageRequest, Long userId);
}
