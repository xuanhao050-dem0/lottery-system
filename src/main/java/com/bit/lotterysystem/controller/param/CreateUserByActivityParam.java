package com.bit.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateUserByActivityParam implements Serializable {
    /**
     * 活动关联人员Id
     */
    @NotBlank(message = "活动用户ID不能为空")
    private Long userId;
    /**
     * 用户名
     */
    @NotBlank(message = "活动用户名不能为空")
    private String userName;
}
