package com.imooc.bootshiro.controller;

import com.imooc.bootshiro.model.UserModel;
import com.imooc.bootshiro.utils.PasswordHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.imooc.bootshiro.realm.MyShiroRealm.userMap;

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
        } catch (UnknownAccountException e){
            log.error("用户登录，用户验证未通过：未知用户！user=" + userName,e);
            return "该用户不存在，请您联系管理员";
        }catch (ExcessiveAttemptsException e) {
            // 获取输错次数
            log.error("用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=" + userName,e);
            return "用户名或密码错误次数大于5次,账户已锁定，2分钟后可再次登录或联系管理员解锁";
        } catch (AuthenticationException e) {
            log.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + userName,e);
            return "用户名或密码不正确";
        }catch (Exception e) {
            log.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + userName, e);
            return "用户登录失败，请您稍后再试";
        }
        return "登陆成功";
    }

    /**
     * 注册账号
     * @param userModel
     * @return
     */
    @RequestMapping("/register")
    public String registerUser(UserModel userModel){

        UserModel user = (UserModel) userMap.get(userModel.getUserName());
        if(user!=null){
            return "用户已存在";
        }
        try {
            //shiro Md5 2次加密，加盐,
            PasswordHelper.encryptPassword(userModel);
            System.out.println(userModel.getPassword());
            userMap.put(userModel.getUserName(),userModel);

            return "success"+ userMap.size();
        } catch (Exception e) {
           return "error";
        }
    }




}
