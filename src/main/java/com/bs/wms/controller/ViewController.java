package com.bs.wms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/{module}/{name}")
    public ModelAndView changeView(ModelAndView modelAndView, @PathVariable String module, @PathVariable String name, @RequestParam(required = false) Map param) {
        modelAndView.setViewName(module + "/" + name);
        modelAndView.addObject("param", param);
        return modelAndView;
    }
}
