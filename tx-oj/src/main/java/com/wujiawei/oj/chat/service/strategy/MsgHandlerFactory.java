package com.wujiawei.oj.chat.service.strategy;

import com.wujiawei.oj.exception.ThrowUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wujiawei
 * @date 2023/12/29 18:28:05
 * 注释：
 */
public class MsgHandlerFactory {
    private static final Map<Integer, AbstractMsghandler> STRATEGY_MAP = new HashMap<>();

    public static void register(Integer code, AbstractMsghandler strategy) {
        STRATEGY_MAP.put(code, strategy);
    }


    public static AbstractMsghandler getStrategyNoNull(Integer code) {
        AbstractMsghandler msghandler = STRATEGY_MAP.get(code);
        ThrowUtils.throwIf(msghandler == null, "没有对应的消息处理器");
        return msghandler;
    }
}
