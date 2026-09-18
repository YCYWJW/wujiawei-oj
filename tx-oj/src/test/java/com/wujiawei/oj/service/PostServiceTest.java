package com.wujiawei.oj.service;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子服务测试
 *
 * @author wujiawei
 * @date 2023/1/24 3:44:13
 * 注释：
 */
@SpringBootTest
class PostServiceTest {

    @Resource
    private PostService postService;

    @Test
    void searchFromEs() {
//        PostQueryRequest postQueryRequest = new PostQueryRequest();
//        postQueryRequest.setUserId(1L);
//        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
//        Assertions.assertNotNull(postPage);
    }

}