package com.wujiawei.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiawei.oj.mapper.CourseFavourMapper;
import com.wujiawei.oj.model.entity.course.CourseFavour;
import com.wujiawei.oj.service.CourseFavourService;
import org.springframework.stereotype.Service;


@Service("courseFavourService")
public class CourseFavourServiceImpl extends ServiceImpl<CourseFavourMapper, CourseFavour> implements CourseFavourService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<CourseFavourEntity> page = this.page(
//                new Query<CourseFavourEntity>().getPage(params),
//                new QueryWrapper<CourseFavourEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}