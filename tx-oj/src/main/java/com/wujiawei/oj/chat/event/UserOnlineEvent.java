package com.wujiawei.oj.chat.event;

import com.wujiawei.oj.model.entity.user.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author wujiawei
 * @date 2024/1/7 21:51:54
 * 注释：
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private final User user;


    public UserOnlineEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
