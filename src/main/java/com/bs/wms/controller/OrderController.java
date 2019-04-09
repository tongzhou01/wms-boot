package com.bs.wms.controller;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.constant.MsgConstant;
import com.bs.wms.dto.SaveOrderDTO;
import com.bs.wms.dto.SendEmailDTO;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import com.bs.wms.vo.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @RestController 控制器，默认返回json数据
 * @RequestMapping url映射
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 获取订单列表
     *
     * @param orderInfoQuery
     * @return
     */
    @GetMapping
    public Page<OrderInfoVO> getOrderList(OrderInfoQuery orderInfoQuery) {
        return orderInfoService.listOrder(orderInfoQuery);
    }

    /**
     * 创建订单
     *
     * @param saveOrderDTO
     * @return
     */
    @PostMapping
    public R saveOrder(@RequestBody SaveOrderDTO saveOrderDTO) {
        return orderInfoService.saveOrder(saveOrderDTO);
    }

    /**
     * 获取订单详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getOrder(@PathVariable Long id) {
        return orderInfoService.getOrder(id);
    }

    /**
     * 更新订单信息
     *
     * @param saveOrderDTO
     * @return
     */
    @PutMapping
    public R updateOrder(@RequestBody SaveOrderDTO saveOrderDTO) {
        return orderInfoService.updateOrder(saveOrderDTO);
    }

    /**
     * 删除订单
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteOrder(@PathVariable Long id) {
        return orderInfoService.deleteOrder(id);
    }

    /**
     * 打印订单
     *
     * @param modelAndView
     * @return
     */
    @GetMapping("/print/{id}")
    public ModelAndView print(ModelAndView modelAndView, @PathVariable Long id) {
        R order = orderInfoService.getOrder(id);
        modelAndView.addObject(MsgConstant.DATA, (OrderInfoVO)order.get(MsgConstant.DATA));
        modelAndView.setViewName("order/detail");
        return modelAndView;
    }

    /**
     * 发送邮件
     * id 订单ID
     * @return
     */
    @PostMapping("/mail")
    public R sendMail(@RequestBody SendEmailDTO sendEmailDTO) {
        return orderInfoService.sendMail(sendEmailDTO);
    }

    /**
     * 获取月度统计
     * @return
     */
    @GetMapping("/count/{year}/{day}")
    public R getCount(@PathVariable Integer year, @PathVariable Integer day) {
        return orderInfoService.getCount(year, day);
    }

}
