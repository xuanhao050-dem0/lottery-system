package com.bit.lotterysystem.controller.result;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrizeInfoResult {
    private Long id;
    /**
     * 奖品图
     */
    private String imageUrl;
    private String prizeName;
    private String description;
    private BigDecimal price;
}
