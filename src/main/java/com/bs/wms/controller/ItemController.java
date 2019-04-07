package com.bs.wms.controller;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.entity.OrderItem;
import com.bs.wms.query.OrderItemQuery;
import com.bs.wms.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    OrderItemService orderItemService;

    /**
     * 获取商品列表
     *
     * @param orderItemQuery
     * @return
     */
    @GetMapping
    public Page<OrderItem> getOrderItemList(OrderItemQuery orderItemQuery) {
        return orderItemService.listOrderItem(orderItemQuery);
    }

    /**
     * 创建商品
     *
     * @param orderItem
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public R saveOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.saveOrderItem(orderItem);
    }

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getOrderItem(@PathVariable Long id) {
        return orderItemService.getOrderItem(id);
    }

    /**
     * 更新商品信息
     *
     * @param orderItem
     * @return
     */
    @PutMapping
    public R updateOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItem(orderItem);
    }

    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteOrderItem(@PathVariable Long id) {
        return orderItemService.deleteOrderItem(id);
    }
}
