package com.gobestsoft.mamage.exception;


/**
 * @author gobestsoft
 * @Description 业务异常的封装
 * @date 2016年11月12日 下午5:05:10
 */
public class BusinessException extends RuntimeException {

    public BusinessException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getMessage());
    }


    public BusinessException(String message) {
        super(message);
    }
}
