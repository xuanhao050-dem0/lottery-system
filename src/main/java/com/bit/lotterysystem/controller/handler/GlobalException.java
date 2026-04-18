package com.bit.lotterysystem.controller.handler;

import com.bit.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ControllerException;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.common.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 * service(抛出异常)--》controller(无法处理)--》springboot 框架处理
 *
 *
 */
@RestControllerAdvice

public class GlobalException {

    public static final Logger logger= LoggerFactory.getLogger(GlobalException.class);

    /**
     * 处理service中的异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<?> serviceException(ServiceException e){
        //打印错误日志
        logger.error("serviceException: ",e);

        //返回错误结果
        return Result.error(
                e.getCode(),
                e.getMessage()
        );
    }

    /**
     * 处理controller中的异常
     */
    @ExceptionHandler(value = ControllerException.class)
    public Result<?> controllerException(ControllerException e){
        //打印错误日志
        logger.error("controllerException: ",e);

        //返回错误结果
        return Result.error(
                e.getCode(),
                e.getMessage()
        );
    }

    /**
     * 处理全局中的异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<?> Exception(Exception e){
        //打印错误日志
        logger.error("服务异常:",e);

        //返回错误结果
        return Result.error(
                GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR

        );
    }

}
