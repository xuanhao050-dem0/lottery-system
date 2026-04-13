package com.bit.lotterysystem.common.exception;

import com.bit.lotterysystem.common.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ServiceException extends RuntimeException{
    /**
     * 错误码
     * @see com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants
     */
    private Integer code;
    private String message;
    public ServiceException(ErrorCode errorCode){
        this(errorCode.getCode(),errorCode.getMessage());
    }
}
