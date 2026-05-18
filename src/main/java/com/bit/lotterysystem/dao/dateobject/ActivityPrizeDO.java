package com.bit.lotterysystem.dao.dateobject;

import lombok.Data;

@Data
public class ActivityPrizeDO {
    private Long activityId;
    private Long prizeId;
    private Integer prizeCount;
    private String prizeTiers;
    private String status;
}
