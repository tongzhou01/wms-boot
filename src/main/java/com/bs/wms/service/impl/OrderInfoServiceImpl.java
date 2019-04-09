package com.bs.wms.service.impl;

import com.alibaba.fastjson.JSON;
import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.constant.BaseConstant;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
            String orderNo = DateUtil.getCurrentTimeByDay().replace("-", "").concat(Integer.toString(maxId + 10001));// 生成订单号
            orderInfo.setOrderNo(orderNo);
            orderInfo.setCreateTime(new Date());
            orderInfo.setModifyTime(new Date());
            // 计算商品总价
            BigDecimal totalAmount = orderItems.stream().map(OrderItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            orderInfo.setTotalAmount(totalAmount);
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
    public R getCount(Integer year , Integer month) {
        String yearDay = year + "-" + (month < 10 ? "0" + month: month);// 月份<10 前面补0
        List<CountVO> countVOList = orderInfoDao.getCount(yearDay);// 查询统计信息
        Map<String, List<CountVO>> companyNameGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getCompanyName));// 按公司名称分组
        List list = new ArrayList();// 接收处理完的数据
        int days = DateUtil.getDaysByYearMonth(year, month);
        companyNameGroup.forEach((key, countVOS) -> {// 遍历每个公司
            CountDataVO countDataVO = new CountDataVO();
            BigDecimal[] countArr = initArray(days);// 每天的交易金额初始化为0
            countVOS.forEach(countVO -> {// 处理每个公司的统计数据，无数据为0，有数据填充
                countArr[countVO.getDay() - 1] = countVO.getTotalAmount();// 数据填充
            });
            countDataVO.setName(key);
            countDataVO.setType(BaseConstant.LINE_TYPE);// 线型图
            countDataVO.setData(countArr);
            list.add(countDataVO);
        });

        // 统计求和
        CountDataVO countDataVO = new CountDataVO();
        BigDecimal[] totalArr = initArray(days);
        Map<Integer, List<CountVO>> dayGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getDay));// 按天分组
        dayGroup.forEach((day, countVOS) -> {// 遍历每一天
            BigDecimal totalAmountOfDay = countVOS.stream().map(CountVO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);// 一天总金额
            totalArr[day - 1] = totalAmountOfDay;
        });
        countDataVO.setType(BaseConstant.LINE_TYPE);
        countDataVO.setName("总计");
        countDataVO.setData(totalArr);
        list.add(countDataVO);

        // X轴坐标值
        int[] categories = new int[days];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = i + 1;
        }
        return R.setData(list).put("categories", categories).put("legend", companyNameGroup.keySet());
    }

    /**
     * 初始化数组
     * @param size
     * @return
     */
    private BigDecimal[] initArray(int size) {
        BigDecimal[] decimalArr = new BigDecimal[size];
        for (int i = 0; i < decimalArr.length; i++) {
            decimalArr[i] = BigDecimal.ZERO;
        }
        return decimalArr;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        CountVO countVO1 = new CountVO("小米", 2019, 4, 1, BigDecimal.ONE);
        CountVO countVO2 = new CountVO("小米", 2019, 4, 2, BigDecimal.ONE);
        CountVO countVO3 = new CountVO("小米", 2019, 4, 2, BigDecimal.ONE);
        CountVO countVO4 = new CountVO("大米", 2019, 4, 4, BigDecimal.ONE);
        CountVO countVO5 = new CountVO("大米", 2019, 4, 5, BigDecimal.ONE);
        List<CountVO> countVOList = new ArrayList<>();
        countVOList.add(countVO1);
        countVOList.add(countVO2);
        countVOList.add(countVO3);
        countVOList.add(countVO4);
        countVOList.add(countVO5);
        int days = DateUtil.getDaysByYearMonth(2019, 4);

        Map<String, List<CountVO>> companyNameGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getCompanyName));

        Map<Integer, List<CountVO>> dayGroup = countVOList.stream().collect(Collectors.groupingBy(CountVO::getDay));// 按天分组
        BigDecimal[] totalArr = new OrderInfoServiceImpl().initArray(days);
        dayGroup.forEach((day, countVOS) -> {// 遍历每一天
            BigDecimal totalAmountOfDay = countVOS.stream().map(CountVO::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);// 一天总金额
            totalArr[day - 1] = totalAmountOfDay;
        });

        System.out.println(JSON.toJSONString(companyNameGroup));
        System.out.println(JSON.toJSONString(dayGroup));
        System.out.println("totalArr=" + JSON.toJSONString(totalArr));
        List list = new ArrayList();
        System.out.println(days);
        companyNameGroup.forEach((key, countVOS) -> {
            CountDataVO countDataVO = new CountDataVO();
            BigDecimal[] countArr = new OrderInfoServiceImpl().initArray(days);
            countVOS.forEach(countVO -> {
                countArr[countVO.getDay() - 1] = countVO.getTotalAmount();
            });
            countDataVO.setName(key);
            countDataVO.setData(countArr);
            list.add(countDataVO);
        });
        System.out.println("list = " + JSON.toJSONString(list));
    }
}
