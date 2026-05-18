package com.bit.lotterysystem.dao.dateobject;

import lombok.Data;

@Data
public class ActivityUserDO {
    private Long activityId;
    private Long userId;
    private String userName;
    private String status;

}
