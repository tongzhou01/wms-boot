package com.bs.wms.controller;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.dto.SaveOrderDto;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import com.bs.wms.vo.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
     * @param saveOrderDto
     * @return
     */
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public R saveOrder(@RequestBody SaveOrderDto saveOrderDto) {
        return orderInfoService.saveOrder(saveOrderDto);
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
     * @param saveOrderDto
     * @return
     */
    @PutMapping
    public R updateOrder(@RequestBody SaveOrderDto saveOrderDto) {
        return orderInfoService.updateOrder(saveOrderDto);
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

}
