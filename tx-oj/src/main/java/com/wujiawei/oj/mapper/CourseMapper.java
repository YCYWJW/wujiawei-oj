package com.wujiawei.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wujiawei.oj.common.PageRequest;
import com.wujiawei.oj.model.entity.course.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wujiawei
 * @email
 * @date 2024-03-22 21:27:47
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    void reduceCourseTimeAndNoduleCount(@Param("courseId") Long courseId, @Param("times") Long times,
                                        @Param("noduleCount") Integer noduleCount);

    void increaseCourseTimeAndNoduleCount(@Param("courseId") Long courseId, @Param("times") Long times,
                                          @Param("noduleCount") Integer noduleCount);

    Page<Course> getUserFavourPage(Page<Course> page, Long userId, PageRequest pageRequest);
}
