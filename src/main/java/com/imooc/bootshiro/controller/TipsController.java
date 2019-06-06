package com.imooc.bootshiro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tips")
public class TipsController {


    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "没有权限,返回登录页面";
    }

}
