package com.bit.lotterysystem.dao.dateobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ActivityDO extends BaseDO{
    private String activityName;
    private String description;
    private String status;
}
