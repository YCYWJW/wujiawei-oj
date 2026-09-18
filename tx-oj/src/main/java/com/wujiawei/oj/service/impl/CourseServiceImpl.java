package com.wujiawei.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiawei.oj.aop.AuthInterceptor;
import com.wujiawei.oj.mapper.CourseMapper;
import com.wujiawei.oj.model.entity.course.Course;
import com.wujiawei.oj.model.entity.course.CourseFavour;
import com.wujiawei.oj.model.entity.user.User;
import com.wujiawei.oj.model.vo.course.CourseSearchItemVO;
import com.wujiawei.oj.service.CourseFavourService;
import com.wujiawei.oj.service.CourseService;
import com.wujiawei.oj.service.adapter.CourseAdapter;
import com.wujiawei.oj.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    UserCache userCache;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseFavourService courseFavourService;

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<CourseCourseEntity> page = this.page(
//                new Query<CourseCourseEntity>().getPage(params),
//                new QueryWrapper<CourseCourseEntity>()
//        );
//
//        return new PageUtils(page);
//    }


    /**
     *
     * @param list
     * @return
     */
    @Override
    public List<CourseSearchItemVO> getCourseSearchItemVOsByCourse(List<?> list) {
        User loginUser = AuthInterceptor.userThreadLocal.get();
        List<CourseSearchItemVO> collect = list.stream().map(item -> {
            Course course = (Course) item;
            User user = userCache.get(course.getUserId());
            // 判断是否已收藏
            int count = courseFavourService.count(new QueryWrapper<CourseFavour>().lambda()
                    .eq(CourseFavour::getCourseId, course.getId())
                    .eq(CourseFavour::getUserId, loginUser.getId()));
            boolean isFavour = count > 0 ? true : false;
            CourseSearchItemVO courseSearchItemVO = CourseAdapter.buildCourseSearchItemVOByCourse(course, user, isFavour);
            return courseSearchItemVO;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<Course> getCoursesByIdAndUserId(Long[] courseIds, Long userId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Course::getUserId, userId)
                .in(Course::getId, courseIds);
        return this.list(wrapper);
    }

    @Override
    public void increaseCourseTimeAndNoduleCount(Long courseId, Long times, Integer noduleCount) {
        courseMapper.increaseCourseTimeAndNoduleCount(courseId, times, noduleCount);
    }

    @Override
    public void reduceCourseTimeAndNoduleCount(Long courseId, Long times, Integer noduleCount) {
        courseMapper.reduceCourseTimeAndNoduleCount(courseId, times, noduleCount);
    }
}