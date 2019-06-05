package com.imooc.bootshiro.realm;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;

import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //集群中可能会导致验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    //解决方案，利用ehcache、redis（记录错误次数）和mysql数据库（锁定）的方式处理：密码输错次数限制，或两者结合使用
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(EhCacheManager ehCacheManager) {
        //读取ehcache中配置的登录限制锁定时间
        passwordRetryCache=ehCacheManager.getCache("passwordRetryCache");
    }

    /**
     * 在回调方法doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info)中进行身份认证的密码匹配，
     * </br>这里我们引入了Ehcahe用于保存用户登录次数，如果登录失败retryCount变量则会一直累加，如果登录成功，那么这个count就会从缓存中移除，
     * </br>从而实现了如果登录次数超出指定的值就锁定。
     * @param token
     * @param info
     * @return
     */
   

}
