package com.bit.lotterysystem.dao.dateobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrizeDO extends BaseDO{
    private String prizeName;
    private String description;
    private BigDecimal price;
    private String imageUrl;
}
