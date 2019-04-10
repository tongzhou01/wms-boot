package com.bs.wms.service.impl;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dao.OrderInfoDao;
import com.bs.wms.dao.OrderItemDao;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderItemQuery;
import com.bs.wms.service.OrderItemService;
import com.bs.wms.vo.OrderItemVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    OrderInfoDao orderInfoDao;

    @Override
    public Page<OrderItemVO> listOrderItem(OrderItemQuery orderItemQuery) {
        Page<OrderItemVO> page = new Page<>();
        PageHelper.startPage(orderItemQuery.getPageNum(), orderItemQuery.getPageSize());//分页
        List<OrderItemVO> orderItemList = orderItemDao.listOrderItem(orderItemQuery);// 查询商品列表
        page.setRows(orderItemList);
        return page;
    }

    @Override
    public R saveOrderItem(OrderItem orderItem) {
        try {
            Long orderId = orderItem.getOrderId();
            if (orderId == null) {
                return R.error("订单不存在");
            }
            // 新增商品信息
            orderItem.setCreateTime(new Date());
            dealAmount(orderItem);
            orderItemDao.insertSelective(orderItem);
            //更新订单金额
            orderInfoDao.updateTotalAmount(orderId);
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
            Long orderId = orderItem.getOrderId();
            if (orderId == null) {
                return R.error("订单不存在");
            }
            // 更新商品信息
            dealAmount(orderItem);
            orderItemDao.updateByPrimaryKeySelective(orderItem);
            //更新订单金额
            orderInfoDao.updateTotalAmount(orderId);
        } catch (Exception e) {
            R.error();
        }
        return R.ok();
    }

    private void dealAmount(OrderItem orderItem) {
        orderItem.setModifyTime(new Date());
        BigDecimal unitPrice = orderItem.getUnitPrice();// 单价
        Integer deliveryNumber = orderItem.getDeliveryNumber();// 数量
        BigDecimal multiply = unitPrice.multiply(new BigDecimal(deliveryNumber));// 乘积
        orderItem.setAmount(multiply);
    }

    @Override
    public R deleteOrderItem(Long id) {
        try {
            OrderItem orderItem = orderItemDao.selectByPrimaryKey(id);
            // 删除商品
            orderItemDao.deleteByPrimaryKey(id);
            //更新订单金额
            orderInfoDao.updateTotalAmount(orderItem.getOrderId());
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }
}
