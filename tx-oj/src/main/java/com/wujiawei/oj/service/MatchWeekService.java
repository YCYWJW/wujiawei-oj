package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.match.WeekMatch;
import com.wujiawei.oj.model.vo.match.WeekMatchVO;

import java.util.List;

/**
 * @author wujiawei
 * @email
 * @date 2024-03-13 15:00:49
 */
public interface MatchWeekService extends IService<WeekMatch> {
    WeekMatch getLastSessionMatch();

    WeekMatchVO getNextMatch();

    List<WeekMatchVO> getHistoryMatch();

    WeekMatchVO getLastWeekMatch();

//    PageUtils queryPage(Map<String, Object> params);
}

