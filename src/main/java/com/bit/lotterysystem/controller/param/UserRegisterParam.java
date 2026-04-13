package com.bit.lotterysystem.controller.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterParam implements Serializable {
    private String name;
    private String mail;
    private String phoneNumber;
    private String password;
    private String identity;

}
