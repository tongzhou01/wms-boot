package com.bs.wms.controller;

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

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.setViewName("user/list");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView login(ModelAndView modelAndView, HttpSession httpSession) {
        httpSession.removeAttribute(BaseConstant.USERNAME);
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession httpSession, ModelAndView modelAndView) {
        SysUser sysUser = sysUserService.getUserByName(username);
        if (sysUser == null) {
            modelAndView.addObject("error","用户不存在");
            return modelAndView;
        } else {
            if (sysUser.getPassword().equals(MD5Util.MD5Encode(password))) {
                httpSession.setAttribute(BaseConstant.USERNAME, username);
                httpSession.setAttribute(BaseConstant.REAL_NAME, sysUser.getRealName());
                httpSession.setMaxInactiveInterval(30*60);
                modelAndView.setViewName("redirect:/");
                return modelAndView;
            } else {
                modelAndView.addObject("error","密码错误");
                return modelAndView;
            }
        }
    }
}
