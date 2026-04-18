package com.bit.lotterysystem.dao.dateobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 包装类
 * 1.作为语义标签，看到encrypt，就知道这是敏感信息
 */
public class Encrypt {
    private String value;


}
