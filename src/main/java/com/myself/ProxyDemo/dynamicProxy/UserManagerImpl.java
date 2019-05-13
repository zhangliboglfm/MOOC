package com.myself.ProxyDemo.dynamicProxy;

/**
 *用户管理实现类
 */
public class UserManagerImpl implements UserManager {
    @Override
    public void findUser() {
        System.out.println("*****UserManagerImpl实现类执行*******");
    }
}
