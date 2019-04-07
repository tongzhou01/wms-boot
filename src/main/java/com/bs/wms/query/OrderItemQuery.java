package com.bs.wms.query;

import com.bs.wms.common.entity.Query;

public class OrderItemQuery extends Query {

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
