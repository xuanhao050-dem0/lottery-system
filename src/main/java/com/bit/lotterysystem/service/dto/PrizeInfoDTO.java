package com.bit.lotterysystem.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrizeInfoDTO {
    private Long id;
    /**
     * 奖品图
     */
    private String imageUrl;
    private String prizeName;
    private String description;
    private BigDecimal price;
}
