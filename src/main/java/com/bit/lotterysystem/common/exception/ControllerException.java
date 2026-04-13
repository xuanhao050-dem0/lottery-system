package com.bit.lotterysystem.common.exception;

import com.bit.lotterysystem.common.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//Data生成自己的equal和hashcode
@Data
@EqualsAndHashCode(callSuper = true)

@AllArgsConstructor
@NoArgsConstructor
public class ControllerException extends RuntimeException{
    /**
     * 错误码
     * @see com.bit.lotterysystem.common.errorcode.ControllerErrorCodeConstants
     */
    private Integer code;

    private String message;


    public ControllerException(ErrorCode errorCode){
        this(errorCode.getCode(),errorCode.getMessage());
    }
}
