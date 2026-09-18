package com.wujiawei.oj.exception;

import com.wujiawei.oj.model.enume.TxCodeEnume;
import com.wujiawei.oj.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author wujiawei
 * @date 2023/1/24 3:44:13
 * 注释：
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public R businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return R.error(TxCodeEnume.COMMON_SYSTEM_UNKNOWN_EXCEPTION);
    }
}
