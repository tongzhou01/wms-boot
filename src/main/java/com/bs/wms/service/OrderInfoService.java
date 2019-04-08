package com.bs.wms.service;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dto.SaveOrderDTO;
import com.bs.wms.dto.SendEmailDTO;
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
     * @param saveOrderDTO
     * @return
     */
    R saveOrder(SaveOrderDTO saveOrderDTO);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    R getOrder(Long id);

    /**
     * 更新订单信息
     * @param saveOrderDTO
     * @return
     */
    R updateOrder(SaveOrderDTO saveOrderDTO);

    /**
     * 删除订单
     * @param id
     * @return
     */
    R deleteOrder(Long id);

    /**
     * 发送邮件
     * @param sendEmailDTO
     * @return
     */
    R sendMail(SendEmailDTO sendEmailDTO);
}
