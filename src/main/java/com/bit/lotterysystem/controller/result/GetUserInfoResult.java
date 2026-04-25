package com.bit.lotterysystem.controller.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserInfoResult implements Serializable {
    private Long id;
    private String userName;
    private String identity;
}
