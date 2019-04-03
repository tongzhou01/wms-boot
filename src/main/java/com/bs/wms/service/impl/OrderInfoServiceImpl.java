package com.bs.wms.service.impl;

import com.bs.wms.entity.OrderInfo;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public List<OrderInfo> getOrderList(OrderInfoQuery orderInfoQuery) {
//        PageHelper.startPage()
//        List list = orderInfoService.selectOrderList(orderInfoQuery);
        return null;
    }
}
