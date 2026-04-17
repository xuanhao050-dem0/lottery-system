package com.bit.lotterysystem.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserIdentityEnum {

    ADMIN("管理员"),
    NORMAl("普通用户");

    private final String message;

    /**
     * 与枚举类型匹对的方法
     */
    public static UserIdentityEnum forName(String name){
        for (UserIdentityEnum userIdentityEnum:UserIdentityEnum.values()){
            if (userIdentityEnum.name().equalsIgnoreCase(name)){
                return userIdentityEnum;
            }
        }
           return null;

    }
}
