package com.bit.lotterysystem.service.dto;

import com.bit.lotterysystem.service.enums.UserIdentityEnum;
import lombok.Data;

@Data
public class UserLoginDTO {
    private UserIdentityEnum identity;
    private String token;
}
