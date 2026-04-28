package com.bit.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrizeUploadParam {
    @NotBlank(message = "奖品名不能为空")
    private String prizeName;
    private String description;
    //@NotBlank(message = "价值不能为空")
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
}
