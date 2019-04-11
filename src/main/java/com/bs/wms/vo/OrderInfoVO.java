package com.bs.wms.vo;

import com.bs.wms.entity.OrderInfo;

import java.util.List;

/**
 * 订单展示对象
 */
public class OrderInfoVO extends OrderInfo {

    /**
     * 商品列表
     */
    private List<OrderItemVO> orderItems;

    public List<OrderItemVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemVO> orderItems) {
        this.orderItems = orderItems;
    }
}
