package com.bs.wms.controller;

import com.bs.wms.common.entity.R;
import com.bs.wms.constant.BaseConstant;
import com.bs.wms.entity.SysUser;
import com.bs.wms.service.SysUserService;
import com.bs.wms.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    SysUserService sysUserService;

    @GetMapping
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public R login(@RequestParam String username, @RequestParam String password, HttpSession httpSession) {
        SysUser sysUser = sysUserService.getUserByName(username);
        if (sysUser == null) {
            return R.error("用户不存在");
        } else {
            if (sysUser.getPassword().equals(MD5Util.MD5Encode(password))) {
                httpSession.setAttribute(BaseConstant.USERNAME, username);
                return R.ok();
            } else {
                return R.error("密码错误");
            }
        }
    }
}
