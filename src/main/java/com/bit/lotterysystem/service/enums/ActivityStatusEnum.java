package com.bit.lotterysystem.service.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActivityStatusEnum {
    RUNNING(1,"活动进行中"),
    COMPLETED(2,"活动已完成");


    private final Integer code;
    private final String message;
}
