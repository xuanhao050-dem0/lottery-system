package com.bit.lotterysystem.controller.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateActivityParam implements Serializable {
    /**
     * 活动名
     */
    @NotBlank(message = "活动名不能为空")
    private String name;

    /**
     * 活动描述
     */
    @NotBlank(message = "活动描述不能为空")
    private String description;

    /**
     * 活动圈选人员
     */
    @NotEmpty(message = "活动人员不能为空")
    @Valid
    private List<CreateUserByActivityParam> createUserByActivityList;

    /**
     * 活动圈选奖品
     */
    @NotEmpty(message = "活动奖品不能为空")
    @Valid
    private List<CreatePrizeByActivityParam> createPrizeByActivityList;


}
