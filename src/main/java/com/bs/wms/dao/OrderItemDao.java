package com.bs.wms.dao;

import com.bs.wms.common.base.BaseDao;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderItemQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemDao extends BaseDao<OrderItem> {

    List<OrderItem> selectItemByOrderId();

    int batchSave(@Param("orderItems") List<OrderItem> orderItems);

    int deleteByOrderId(Long orderId);

    List<OrderItem> listOrderItem(OrderItemQuery orderItemQuery);
}