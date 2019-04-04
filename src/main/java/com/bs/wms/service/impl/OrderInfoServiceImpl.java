package com.bs.wms.service.impl;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dao.OrderInfoDao;
import com.bs.wms.dao.OrderItemDao;
import com.bs.wms.dto.SaveOrderDto;
import com.bs.wms.entity.OrderInfo;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import com.bs.wms.vo.OrderInfoVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoDao orderInfoDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Override
    public Page<OrderInfoVO> listOrder(OrderInfoQuery orderInfoQuery) {
        Page<OrderInfoVO> page = new Page<>();
        PageHelper.startPage(orderInfoQuery.getPageNum(), orderInfoQuery.getPageSize());//分页查询
        List<OrderInfoVO> orderInfoVOList = orderInfoDao.listOrder();
        page.setRows(orderInfoVOList);
        return page;
    }

    @Override
    public R saveOrder(SaveOrderDto saveOrderDto) {
        List<OrderItem> orderItems = saveOrderDto.getOrderItems();
        OrderInfo orderInfo = saveOrderDto.getOrderInfo();
        orderInfoDao.insertSelective(orderInfo);
        orderItemDao.batchSave(orderItems);
        return null;
    }
}
