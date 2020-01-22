package com.gobestsoft.mamage.config.advice;

import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常捕捉处理
 * create by gutongwei
 * on 2018/8/4 下午1:50
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends ManageBasic {

    /**
     * 全局异常捕捉处理
     * 统一返回
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public String errorHandler(BusinessException ex) {
        log.error("运行时错误{}", ex);
        return "500";
    }


}
