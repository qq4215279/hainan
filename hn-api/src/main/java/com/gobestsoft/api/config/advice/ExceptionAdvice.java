package com.gobestsoft.api.config.advice;

import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.core.basic.HttpBasic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;

/**
 * 全局异常拦截
 * 注意：只有RuntimeException才能进行事务回滚
 * Create by gutongwei
 * 2018/5/18
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice extends HttpBasic {

    /**
     * 全局异常捕捉处理
     * 统一返回
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResult errorHandler(RuntimeException ex) {
        log.error("运行时错误{}", ex);
        return new BaseResult(500, ex.getMessage(), null);
    }

    /**
     * 全局异常捕捉处理
     * 统一返回
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ServletException.class)
    public BaseResult errorHandler(ServletException ex) {
        log.error("错误{}", ex);
        return new BaseResult(400, ex.getMessage(), null);
    }
}
