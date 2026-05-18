package com.bit.lotterysystem.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityPrizeStatusEnum {
    INIT(1,"初始化"),
    COMPLETED(2,"已被抽取");


    private final Integer code;
    private final String message;

    public static ActivityPrizeStatusEnum forName(String name){
        //将枚举项组成一个数组[RUNNING,COMPLETED]
        //每个枚举对象activityStatusEnum，就是一个枚举项RUNNING或COMPLETED
        for (ActivityPrizeStatusEnum activityPrizeStatusEnum:ActivityPrizeStatusEnum.values()){
            if (activityPrizeStatusEnum.name().equalsIgnoreCase(name)){
                return activityPrizeStatusEnum;
            }
        }
        return null;
    }
}
