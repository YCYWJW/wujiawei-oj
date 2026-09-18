package com.wujiawei.oj.service.impl;

import com.wujiawei.oj.mapper.UserEmojiMapper;
import com.wujiawei.oj.model.entity.user.UserEmoji;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wujiawei.oj.service.UserEmojiService;


@Service("userEmojiService")
public class UserEmojiServiceImpl extends ServiceImpl<UserEmojiMapper, UserEmoji> implements UserEmojiService {

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<UserEmojiEntity> page = this.page(
//                new Query<UserEmojiEntity>().getPage(params),
//                new QueryWrapper<UserEmojiEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}
