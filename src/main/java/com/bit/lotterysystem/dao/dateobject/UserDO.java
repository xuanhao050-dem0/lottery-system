package com.bit.lotterysystem.dao.dateobject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends BaseDO{
    private String userName;
    private String email;
    /**
     * 手机号在数据库为加密类型
     */
    private Encrypt phoneNumber;
    /**
     * 密码在数据库中为加密字段，string，没有解密一说
     */
    private String password;
    private String identity;
}
