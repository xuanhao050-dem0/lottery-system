package com.bit.lotterysystem.controller.param;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
//@AllArgsConstructor
public class PageParam implements Serializable {
    /**
     * 当前页：默认为1
     */
    private Integer currentPage=1;

    /**
     * 当前页数量：默认为10
     */
    private Integer currentPageCount=10;

    /**
     * 偏移量
     */
    public Integer offset(){
        return (currentPage-1)*currentPageCount;
    }
}
