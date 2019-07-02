package com.bs.wms.vo;

import java.math.BigDecimal;

/**
 * 统计数据展示对象
 */
public class CountDataVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 数据数组
     */
    private BigDecimal[] data;

    /**
     * 类型
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal[] getData() {
        return data;
    }

    public void setData(BigDecimal[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
