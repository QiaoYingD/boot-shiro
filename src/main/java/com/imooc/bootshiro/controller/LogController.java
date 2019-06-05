package com.imooc.bootshiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {


    @RequestMapping("/login")
    public static String login(String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            if (!subject.isAuthenticated()) {
                return "用户名账号错误";
            }
        }catch (IncorrectCredentialsException ice) {
            // 获取输错次数
            log.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + userName);
            return "用户登录，用户验证未通过：错误的凭证，密码输入错误！user="+userName;
        } catch (AuthenticationException e) {
            log.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + userName);
            return "用户登录，用户验证未通过：认证异常，异常信息如下！user=" + userName;
        }
        return "登陆成功";
    }




}
