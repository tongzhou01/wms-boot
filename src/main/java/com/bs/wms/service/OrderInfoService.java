package com.bs.wms.service;

import com.bs.wms.entity.OrderInfo;
import com.bs.wms.query.OrderInfoQuery;

import java.util.List;

public interface OrderInfoService {

    /**
     * 查询订单列表
     * @param orderInfoQuery
     * @return
     */
    List<OrderInfo> getOrderList(OrderInfoQuery orderInfoQuery);
}
