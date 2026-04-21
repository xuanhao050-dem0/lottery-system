package com.bit.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationCodeParam {
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
