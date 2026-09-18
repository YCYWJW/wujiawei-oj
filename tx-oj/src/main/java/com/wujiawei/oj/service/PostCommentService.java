package com.wujiawei.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wujiawei.oj.model.entity.PostComment;
import com.wujiawei.oj.model.entity.user.User;
import com.wujiawei.oj.model.vo.post.PostCommentVO;
import com.wujiawei.oj.utils.page.PageUtils;
import com.wujiawei.oj.utils.page.PageVO;

import java.util.List;

/**
 * @author wujiawei
 * @date 2023/12/3 1:02:51
 * 注释：
 */
public interface PostCommentService extends IService<PostComment> {
    List<PostCommentVO> getPostCommentVOs(List<?> list, User loginUser);

    boolean thumbComment(Long commentId, int opsValue);

    PageUtils queryPage(PageVO queryVO);
}
