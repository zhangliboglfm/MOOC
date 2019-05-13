package com.myself.ProxyDemo.CglibProxy;

/**
 *用户管理实现类
 */
public class UserManagerImpl {
    public void findUser() {
        System.out.println("*****UserManagerImpl实现类执行*******");
    }
    public final void addUser() {
        System.out.println("*****add User*******");
    }
}
