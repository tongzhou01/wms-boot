package com.bs.wms.service;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dto.SaveOrderDto;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.vo.OrderInfoVO;

public interface OrderInfoService {

    /**
     * 查询订单列表
     *
     * @param orderInfoQuery
     * @return
     */
    Page<OrderInfoVO> listOrder(OrderInfoQuery orderInfoQuery);

    /**
     * 新增订单
     * @param saveOrderDto
     * @return
     */
    R saveOrder(SaveOrderDto saveOrderDto);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    R getOrder(Long id);

    /**
     * 更新订单信息
     * @param saveOrderDto
     * @return
     */
    R updateOrder(SaveOrderDto saveOrderDto);

    R deleteOrder(Long id);
}
