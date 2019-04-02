package com.bs.wms.controller;

import com.bs.wms.entity.SysUser;
import com.bs.wms.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("{id}")
    public SysUser getUser(@PathVariable Long id) {
        return sysUserService.getUserById(id);
    }
}
