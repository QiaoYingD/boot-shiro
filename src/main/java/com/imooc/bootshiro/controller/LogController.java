package com.imooc.bootshiro.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.authc.*;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
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
        boolean flag = false;
        try {
            subject.login(token);
            if (!subject.isAuthenticated()) {
                return "用户名账号错误";
            }
        } catch (UnknownAccountException e){
            log.error("用户登录，用户验证未通过：未知用户！user=" + userName,e);
            return "该用户不存在，请您联系管理员";
        }catch (ExcessiveAttemptsException e) {
            // 获取输错次数
            log.error("用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=" + userName,e);
            return "用户名或密码错误次数大于5次,账户已锁定，2分钟后可再次登录或联系管理员解锁";

        } catch (IncorrectCredentialsException ice) {
            // 获取输错次数
            log.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + userName);
            return "用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + userName;
        } catch (ExcessiveAttemptsException e) {
            log.error(
                    "用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getMobile()" + userName, e);
            return "这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次";
        } catch (AuthenticationException e) {
            log.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + userName,e);
            return "用户名或密码不正确";
        }catch (Exception e) {
            log.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + userName, e);
            return "用户登录失败，请您稍后再试";
        }


        return "登陆成功";
    }


}
