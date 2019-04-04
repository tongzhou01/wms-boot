package com.bs.wms.vo;

import com.bs.wms.entity.OrderInfo;
import com.bs.wms.entity.OrderItem;

import java.util.List;

/**
 * 订单展示对象
 */
public class OrderInfoVO extends OrderInfo {

    /**
     * 商品列表
     */
    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
