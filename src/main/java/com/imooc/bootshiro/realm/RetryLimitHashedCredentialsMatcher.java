package com.imooc.bootshiro.realm;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //集群中可能会导致验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    //解决方案，利用ehcache、redis（记录错误次数）和mysql数据库（锁定）的方式处理：密码输错次数限制，或两者结合使用
    public RetryLimitHashedCredentialsMatcher(EhCacheManager ehCacheManager) {
        //读取ehcache中配置的登录限制锁定时间

    }
}
