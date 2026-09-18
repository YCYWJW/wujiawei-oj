package com.wujiawei.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wujiawei.oj.model.entity.match.OnlinePkMatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author wujiawei
 * @email 
 * @date 2024-03-13 15:00:49
 */
@Mapper
public interface MatchOnlinepkMapper extends BaseMapper<OnlinePkMatch> {

    void finishMatch(@Param("matchId") Long matchId, @Param("userId") Long userId);

    List<OnlinePkMatch> getMatchsByUserId(@Param("userId") Long userId);
}
