package com.imooc.bootshiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @RequestMapping("/query")
    public String query(){
        log.info("ok");
        return "访问通过啦";
    }


}
