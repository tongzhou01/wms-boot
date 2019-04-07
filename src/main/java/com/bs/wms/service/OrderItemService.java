package com.bs.wms.service;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderItemQuery;

public interface OrderItemService {

    Page<OrderItem> listOrderItem(OrderItemQuery orderItemQuery);

    R saveOrderItem(OrderItem orderItem);

    R getOrderItem(Long id);

    R updateOrderItem(OrderItem orderItem);

    R deleteOrderItem(Long id);
}
