package com.bs.wms.service.impl;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dao.OrderItemDao;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderItemQuery;
import com.bs.wms.service.OrderItemService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemDao orderItemDao;

    @Override
    public Page<OrderItem> listOrderItem(OrderItemQuery orderItemQuery) {
        Page<OrderItem> page = new Page<>();
        PageHelper.startPage(orderItemQuery.getPageNum(), orderItemQuery.getPageSize());//分页
        List<OrderItem> orderItemList = orderItemDao.listOrderItem(orderItemQuery);// 查询商品列表
        page.setRows(orderItemList);
        return page;
    }

    @Override
    public R saveOrderItem(OrderItem orderItem) {
        try {
            // 新增商品信息
            orderItem.setCreateTime(new Date());
            orderItem.setModifyTime(new Date());
            orderItemDao.insertSelective(orderItem);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R getOrderItem(Long id) {
        return R.setData(orderItemDao.selectByPrimaryKey(id));
    }

    @Override
    public R updateOrderItem(OrderItem orderItem) {
        try {
            // 更新商品信息
            orderItem.setModifyTime(new Date());
            orderItemDao.updateByPrimaryKeySelective(orderItem);
        } catch (Exception e) {
            R.error();
        }
        return R.ok();
    }

    @Override
    public R deleteOrderItem(Long id) {
        try {
            // 删除商品
            orderItemDao.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }
}
