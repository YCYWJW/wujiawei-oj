package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.course.CourseVideo;

import java.util.List;

/**
 * 
 *
 * @author wujiawei
 * @email 
 * @date 2024-03-22 21:27:47
 */
public interface CourseVideoService extends IService<CourseVideo> {
    List<CourseVideo> listByCourseId(Long courseId);

    void deleteByCourseIds(List<Long> courseIds);


//    PageUtils queryPage(Map<String, Object> params);
}

