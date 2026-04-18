package com.bit.lotterysystem.dao.dateobject;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DO 与数据库表结构一一对应，通过dao层向上传输数据源对象
 */
@Data
public class BaseDO implements Serializable {
    private Long id;
    private Date gmtCreate;
    private Date gmtModify;
}
