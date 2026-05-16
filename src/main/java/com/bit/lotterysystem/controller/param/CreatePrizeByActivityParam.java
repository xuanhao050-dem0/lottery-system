package com.bit.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreatePrizeByActivityParam implements Serializable {
    /**
     * 活动关联的奖品id
     */
    @NotBlank(message = "活动奖品ID不能为空")
    private Long prizeId;
    /**
     * 奖品数量
     */
    @NotBlank(message = "活动奖品数量不能为空")
    private Integer prizeCount;
    /**
     * 奖品等级
     */
    @NotBlank(message = "活动奖品等级不能为空")
    private String prizeLevel;
}
