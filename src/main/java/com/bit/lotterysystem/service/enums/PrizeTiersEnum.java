package com.bit.lotterysystem.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrizeTiersEnum {
    FIRST_PRIZE(1,"一等奖"),
    SECOND_PRIZE(2,"二等奖"),
    THIRD_PRIZE(3,"三等奖");


    private final Integer code;
    private final String message;

    public static PrizeTiersEnum forName(String name){
        //将枚举项组成一个数组[RUNNING,COMPLETED]
        //每个枚举对象activityStatusEnum，就是一个枚举项RUNNING或COMPLETED
        for (PrizeTiersEnum prizeTiersEnum:PrizeTiersEnum.values()){
            if (prizeTiersEnum.name().equalsIgnoreCase(name)){
                return prizeTiersEnum;
            }
        }
        return null;
    }
}
