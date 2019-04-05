package com.bs.wms.controller;

import com.bs.wms.common.entity.Page;
import com.bs.wms.common.entity.R;
import com.bs.wms.entity.SysUser;
import com.bs.wms.query.SysUserQuery;
import com.bs.wms.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sys/user")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    /**
     * 获取用户列表
     *
     * @param sysUserQuery
     * @return
     */
    @GetMapping
    public Page<SysUser> getSysUserList(SysUserQuery sysUserQuery) {
        return sysUserService.listSysUser(sysUserQuery);
    }

    /**
     * 创建用户
     *
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public R saveSysUser(@RequestBody SysUser sysUser) {
        return sysUserService.saveSysUser(sysUser);
    }

    /**
     * 获取用户详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getSysUser(@PathVariable Long id) {
        return sysUserService.getSysUser(id);
    }

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    @PutMapping
    public R updateSysUser(@RequestBody SysUser sysUser) {
        return sysUserService.updateSysUser(sysUser);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deleteSysUser(@PathVariable Long id) {
        return sysUserService.deleteSysUser(id);
    }
}
