package com.wujiawei.oj.model.vo.match;

import com.wujiawei.oj.model.vo.question.QuestionVO;
import com.wujiawei.oj.model.vo.user.UserShowVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PkMatchStartVO {

    /**
     * 比赛题目
     */
    QuestionVO questionVO;
    /**
     * 对手信息
     */
    UserShowVO userShowVO;
    /**
     * 比赛信息
     */
    OnlinePkMatchVO matchVO;
}
