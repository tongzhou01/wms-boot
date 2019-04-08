package com.bs.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dao.OrderInfoDao;
import com.bs.wms.dao.OrderItemDao;
import com.bs.wms.dto.SaveOrderDTO;
import com.bs.wms.dto.SendEmailDTO;
import com.bs.wms.entity.OrderInfo;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import com.bs.wms.util.DateUtil;
import com.bs.wms.vo.CountDataVO;
import com.bs.wms.vo.CountVO;
import com.bs.wms.vo.OrderInfoVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        List<OrderInfoVO> orderInfoVOList = orderInfoDao.listOrder(orderInfoQuery);
        page.setRows(orderInfoVOList);
        return page;
    }

    @Override
    public R saveOrder(SaveOrderDTO saveOrderDTO) {
        List<OrderItem> orderItems = saveOrderDTO.getOrderItems();
        OrderInfo orderInfo = saveOrderDTO.getOrderInfo();
        try {
            // 新增订单信息
            Integer maxId = orderInfoDao.selectMaxId();
            if (maxId == null) {
                maxId = 1;
            }
            String orderNo = DateUtil.getCurrentTimeByDay().replace("-", "").concat(Integer.toString(maxId + 10001));
            orderInfo.setOrderNo(orderNo);
            orderInfo.setCreateTime(new Date());
            orderInfo.setModifyTime(new Date());
            orderInfoDao.insertSelective(orderInfo);
            Long orderId = orderInfo.getId();
            if (orderId != null && orderItems != null && orderItems.size() > 0) {
                // 新增商品信息
                orderItems.forEach(orderItem -> orderItem.setOrderId(orderId));
                orderItemDao.batchSave(orderItems);
            }
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R getOrder(Long id) {
        return R.setData(orderInfoDao.getOrder(id));
    }

    @Override
    public R updateOrder(SaveOrderDTO saveOrderDTO) {
        OrderInfo orderInfo = saveOrderDTO.getOrderInfo();
        List<OrderItem> orderItems = saveOrderDTO.getOrderItems();
        try {
            // 更新订单信息
            orderInfo.setModifyTime(new Date());
            orderInfoDao.updateByPrimaryKeySelective(orderInfo);
            // 更新商品信息
            orderItems.forEach(orderItem -> {
                orderItem.setModifyTime(new Date());
                orderItemDao.updateByPrimaryKeySelective(orderItem);
            });
        } catch (Exception e) {
            R.error();
        }
        return R.ok();
    }

    @Override
    public R deleteOrder(Long id) {
        try {
            // 删除订单
            int i = orderInfoDao.deleteByPrimaryKey(id);
            if (i > 0) {
                // 删除商品
                orderItemDao.deleteByOrderId(id);
            }
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    @Override
    public R sendMail(SendEmailDTO sendEmailDTO) {
//        MailUtil.sendMail();
        return null;
    }

    @Override
    public R getCount() {
        List<CountVO> countVOList = orderInfoDao.getCount();
        Integer[] countArr = new Integer[12];
        List<CountDataVO> list = new ArrayList<>();
        CountDataVO countDataVO = new CountDataVO();;
        for (int i = 0; i < countArr.length; i++) {
            countArr[i] = 0;
        }



        for (CountVO countVO : countVOList) {
            if (countVO.getCompanyName() != null) {// 公司名称不为空
                if (countVO.getCompanyName().equals(countDataVO.getName())) { // 公司名称已存在

                } else {
                    countDataVO = new CountDataVO();
                    countDataVO.setName(countVO.getCompanyName());
                }
            }
        }
        System.out.println(JSON.toJSON(countArr));
        return null;
    }
}
