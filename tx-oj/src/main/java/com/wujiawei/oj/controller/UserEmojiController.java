package com.wujiawei.oj.controller;

import java.util.Arrays;

import com.wujiawei.oj.model.entity.user.UserEmoji;
import com.wujiawei.oj.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wujiawei.oj.service.UserEmojiService;



/**
 *
 *
 * @author wujiawei
 * @email
 * @date 2023-12-28 23:46:51
 */
@RestController
@RequestMapping("oj/useremoji")
public class UserEmojiController {
    @Autowired
    private UserEmojiService userEmojiService;

    /**
     * 列表
     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = userEmojiService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserEmoji userEmoji = userEmojiService.getById(id);

        return R.ok().put("userEmoji", userEmoji);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEmoji userEmoji){
		userEmojiService.save(userEmoji);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEmoji userEmoji){
		userEmojiService.updateById(userEmoji);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userEmojiService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
