package com.bit.lotterysystem.service.dto;

import com.bit.lotterysystem.service.enums.UserIdentityEnum;
import lombok.Data;

@Data
public class GetUserInfoDTO {
    private Long id;
    private String userName;
    private String email;
    private String phoneNumber;
    private UserIdentityEnum identity;
}
