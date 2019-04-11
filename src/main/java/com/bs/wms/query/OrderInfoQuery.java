package com.bs.wms.query;

import com.bs.wms.common.entity.Query;

public class OrderInfoQuery extends Query {

    /**
     * 订单ID
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
