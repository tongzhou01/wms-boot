package com.bs.wms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * 订单列表页面
     *
     * @return
     */
    @GetMapping("order/list")
    public String getOrderList() {
        return "order/list";
    }
}
