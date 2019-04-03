package com.bs.wms.controller;

import com.bs.wms.entity.OrderInfo;
import com.bs.wms.query.OrderInfoQuery;
import com.bs.wms.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderInfo")
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 获取订单列表
     * @param orderInfoQuery
     * @return
     */
    @GetMapping
    public List<OrderInfo> getOrderList(@RequestParam OrderInfoQuery orderInfoQuery) {
        List orderInfoList = orderInfoService.getOrderList(orderInfoQuery);
        return null;
    }

    /**
     * 创建订单
     * @param orderInfo
     * @return
     */
    @PostMapping(value="/")
    @ResponseStatus(HttpStatus.CREATED)
    public String postOrder(@RequestBody OrderInfo orderInfo) {
        return "success";
    }

    /**
     * 获取订单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public OrderInfo getOrder(@PathVariable Long id) {
        return null;
    }

    /**
     * 更新订单信息
     * @param id
     * @param orderInfo
     * @return
     */
    @PutMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @RequestBody OrderInfo orderInfo) {
        return "success";
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return "success";
    }

}
