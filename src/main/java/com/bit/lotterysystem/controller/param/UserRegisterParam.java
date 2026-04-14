package com.bit.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterParam implements Serializable {
    @NotBlank(message = "名字不能为空")
    private String name;

    @NotBlank(message = "邮箱不能为空")
    private String mail;

    @NotBlank(message = "电话不能为空")
    private String phoneNumber;

    private String password;

    @NotBlank(message = "角色不能为空")
    private String identity;

}
