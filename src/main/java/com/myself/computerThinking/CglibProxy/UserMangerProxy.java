package com.myself.computerThinking.CglibProxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserMangerProxy implements MethodInterceptor {


    /**
     * @param obj 代理对象
     * @param method 委托类方法
     * @param args  方法参数
     * @param proxy 代理方法的MethodProxy对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before:" + method);
        Object object = proxy.invokeSuper(obj, args);
        System.out.println("After:" + method);
        return object;
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserManagerImpl.class);
        enhancer.setCallback(new UserMangerProxy());
        UserManagerImpl userMangerProxy = (UserManagerImpl)enhancer.create();
        userMangerProxy.findUser();
        userMangerProxy.addUser();
    }

}
