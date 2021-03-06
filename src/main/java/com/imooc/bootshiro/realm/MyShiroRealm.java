package com.imooc.bootshiro.realm;

import com.imooc.bootshiro.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.*;

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {


    public static Map<String, Object> userMap = new HashMap<>();

    public static Map<String, Object> roleMap = new HashMap<>();

    static {

        userMap.put("admin", new UserModel("admin", "654407ac2e454fe560337510aa6adb97"));
        userMap.put("lisi", new UserModel("lisi", "654407ac2e454fe560337510aa6adb97"));
        List<String> roleList = new ArrayList<>();
        roleList.add("admin");
        List<String> roleList1 = new ArrayList<>();
        roleList.add("a");
        roleMap.put("admin", roleList);
        roleMap.put("lisi",roleList1);
    }

    /**
     * 授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权
        log.debug("授予角色和权限");
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        UserModel userModel = (UserModel) principalCollection.getPrimaryPrincipal();

            // 普通用户，查询用户的角色，根据角色查询权限
            List<String> roles = (List<String>) roleMap.get(userModel.getUserName());
            if (!roles.isEmpty()) {
                //添加用户对应的角色 可使用用户来添加对应的角色数据
                authorizationInfo.addRoles(roles);
                // TODO 角色对应的权限数据  可使用循环来添加对应的权限数据

        }

        return authorizationInfo;
    }

    /**
     * 登录认证信息
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取登录用户名
        String userName = (String) authenticationToken.getPrincipal();
        UserModel userModel = (UserModel) userMap.get(userName);
        if (userModel == null) {
            //用户不存在
            return null;
        }
        // 密码存在
        // 第一个参数 ，登陆后，需要在session保存数据
        // 第二个参数，查询到密码(加密规则要和自定义的HashedCredentialsMatcher中的HashAlgorithmName散列算法一致)
        // 第三个参数 ，realm名字
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userModel,userModel.getPassword(), ByteSource.Util.bytes(userModel.getUserName()), userModel.getUserName());

        return info;
    }

    public static  void main(String[] args){
        System.out.println( new Md5Hash("123456"));
    }

}
