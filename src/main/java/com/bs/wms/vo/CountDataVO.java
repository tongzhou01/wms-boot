package com.bs.wms.vo;

import java.math.BigDecimal;

public class CountDataVO {

    private String name;

    private BigDecimal[] data;

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
