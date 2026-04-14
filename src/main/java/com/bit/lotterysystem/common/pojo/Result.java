package com.bit.lotterysystem.common.pojo;

import com.bit.lotterysystem.common.errorcode.ErrorCode;
import com.bit.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import lombok.Data;
import org.springframework.util.Assert;

@Data
public class Result <T>{
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 数据
     */
    private T date;
    /**
     * 信息
     */
    private String message;

    /**
     * 构造方法
     */

    public static <T> Result<T> success(T date){
        //new对象
        Result<T> result=new Result<>();
        //赋值
        result.code= GlobalErrorCodeConstants.SUCCESS.getCode();
        result.date=date;
        result.message=GlobalErrorCodeConstants.SUCCESS.getMessage();

        return result;
    }

    public static <T> Result<T> error(Integer code,String message){
        Assert.isTrue(!GlobalErrorCodeConstants.SUCCESS.getCode().equals(code),
                "code不是错误码");

        //new对象
        Result<T> result=new Result<>();

        result.code=code;
        result.message=message;

        return result;
    }

    public static <T> Result<T> error(ErrorCode errorcode){
        return error(errorcode.getCode(),errorcode.getMessage());

    }









}
