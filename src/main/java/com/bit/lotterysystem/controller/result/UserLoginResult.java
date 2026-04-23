package com.bit.lotterysystem.controller.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
public class UserLoginResult {
    private String token;

    private String identity;
}
