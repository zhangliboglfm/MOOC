package com.myself.ProxyDemo.staticProxy;

/**
 * 静态代理类
 */
public class UserMangerProxy  implements UserManager{

    private UserManagerImpl userManager;
    public  UserMangerProxy(UserManagerImpl userManager){
        this.userManager=userManager;
    }

    @Override
    public void findUser() {
        System.out.println("-------执行前调-------------");
        userManager.findUser();
        System.out.println("-------执行后调-------------");
    }

    /**
     * 优点：
     * 代理使客户端不需要知道实现类是什么，怎么做的，而客户端只需知道代理即可（解耦合），对于如上的客户端代码，newUserManagerImpl()可以应用工厂将它隐藏，如上只是举个例子而已。
     *
     * 缺点：
     * 1）代理类和委托类实现了相同的接口，代理类通过委托类实现了相同的方法。这样就出现了大量的代码重复。如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度。
     * 2）代理对象只服务于一种类型的对象，如果要服务多类型的对象。势必要为每一种对象都进行代理，静态代理在程序规模稍大时就无法胜任了。如上的代码是只为UserManager类的访问提供了代理，但是如果还要为其他类如Department类提供代理的话，就需要我们再次添加代理Department的代理类。
     * 举例说明：代理可以对实现类进行统一的管理，如在调用具体实现类之前，需要打印日志等信息，这样我们只需要添加一个代理类，在代理类中添加打印日志的功能，然后调用实现类，这样就避免了修改具体实现类。满足我们所说的开闭原则。但是如果想让每个实现类都添加打印日志的功能的话，就需要添加多个代理类，以及代理类中各个方法都需要添加打印日志功能（如上的代理方法中删除，修改，以及查询都需要添加上打印日志的功能）
     * 即静态代理类只能为特定的接口(Service)服务。如想要为多个接口服务则需要建立很多个代理类。
     * @param args
     */
    public static void main(String[] args) {
        UserManager userManager = new UserMangerProxy(new UserManagerImpl());
        userManager.findUser();
    }
}
