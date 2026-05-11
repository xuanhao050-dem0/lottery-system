package com.bit.lotterysystem.controller.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GetPrizeInfoResult implements Serializable {
    private Integer total;

    private List<PrizeInfo> records;

    @Data
    public static class PrizeInfo implements Serializable{
        private Long id;
        /**
         * 奖品图
         */
        private String imageUrl;
        private String prizeName;
        private String description;
        private BigDecimal price;
    }
}
