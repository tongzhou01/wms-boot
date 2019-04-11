package com.bs.wms.dto;

import java.io.Serializable;

public class SendEmailDTO implements Serializable {
    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 接收地址
     */
    private String receiverAddress;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}