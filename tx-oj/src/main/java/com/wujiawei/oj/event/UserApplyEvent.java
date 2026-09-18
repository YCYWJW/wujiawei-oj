package com.wujiawei.oj.event;

import com.wujiawei.oj.model.entity.user.UserApply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wujiawei
 * @date 2023/12/29 10:17:08
 * 注释：
 */
@Getter
public class UserApplyEvent extends ApplicationEvent {

    private UserApply userApply;

    public UserApplyEvent(Object source, UserApply userApply) {
        super(source);
        this.userApply = userApply;
    }
}
