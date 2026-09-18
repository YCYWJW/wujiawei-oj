package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.course.Course;
import com.wujiawei.oj.model.vo.course.CourseSearchItemVO;

import java.util.List;

/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2024-03-22 21:27:47
 */
public interface CourseService extends IService<Course> {
    List<CourseSearchItemVO> getCourseSearchItemVOsByCourse(List<?> list);

    List<Course> getCoursesByIdAndUserId(Long[] courseIds, Long userId);

    void increaseCourseTimeAndNoduleCount(Long courseId, Long times, Integer noduleCount);

    void reduceCourseTimeAndNoduleCount(Long courseId, Long times, Integer noduleCount);

//    PageUtils queryPage(Map<String, Object> params);
}

