package com.jasmine.doforlove.advice;

import com.jasmine.doforlove.api.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description 全局异常处理器（这种方式只能处理Controller抛出的异常）
 * @author: wanggc
 * @date:  2022/7/14 14:20
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Object handleException(Exception e) {
        return Result.fail("500", e.getMessage());
    }
}
