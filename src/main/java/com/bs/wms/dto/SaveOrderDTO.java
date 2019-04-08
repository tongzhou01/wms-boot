package com.bs.wms.dto;

import com.bs.wms.entity.OrderInfo;
import com.bs.wms.entity.OrderItem;

import java.util.List;

public class SaveOrderDTO {

    private OrderInfo orderInfo;

    private List<OrderItem> orderItems;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
