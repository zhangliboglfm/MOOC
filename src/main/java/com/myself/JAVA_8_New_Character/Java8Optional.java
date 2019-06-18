package com.myself.JAVA_8_New_Character;

import com.myself.JAVA_8_New_Character.extraUtil.Car;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

/**
 * java 1.8 Optional的应用
 */
public class Java8Optional {

    /**
     * https://www.cnblogs.com/zhangboyu/p/7580262.html
     *
     * 创建Optional实例
     *  明确对象不为null的时候，使用of(), 如果对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法
     *  检测是否有值：isPersent()
     *  如果有值，执行后面的lambda表达式 ifPresent(obj-> ...)
     *  过滤值： filter()  返回测试结果为 true 的值。如果测试结果为 false，会返回一个空的 Optional
     *  返回默认值：
     *      orElse(new Car())  如果不为空，new Car() 仍然会执行
     *      orElseGet(()->new Car()); 接受一个Supplier(供应者)  如果不为空，new Car() 不会执行
     *  返回异常： orElseThrow(() -> new IllegalArgumentException())  抛出指定的异常
     *
     *  转换值：
     *      map(car->car.getYear())     // 获取到对应的数据类型，
     *      flatMap(car->car.getOptional()) // 获取到对应的Optional<T> 的包装类
     *
     *
     *    java 9 增强：
     *      or()   方法与 orElse() 和 orElseGet() 类似，它们都在对象为空的时候提供了替代情况。or() 的返回值是由 Supplier 参数产生的另一个 Optional 对象。
     *      ifPresentOrElse()       fPresentOrElse() 方法需要两个参数：一个 Consumer 和一个 Runnable。如果对象包含值，会执行 Consumer 的动作，否则运行 Runnable。
     *      stream() 它通过把实例转换为 Stream 对象，让你从广大的 Stream API 中受益。如果没有值，它会得到空的 Stream；有值的情况下，Stream 则会包含单一值。
     */
    @Test(expected = NoSuchElementException.class)
    public  void createOptional(){
        Optional<Car> optionalCar = Optional.of(  new Car("Jeep", "Wrangler", 2011));
        System.out.println(optionalCar.get());
        optionalCar.ifPresent(car -> System.out.println(car.getYear()));
        Optional<Car> emptyOpt = Optional.empty();
        emptyOpt.orElse(new Car("Jeep","Wrangler",2011));
        emptyOpt.orElseGet(()->new Car("Jeep","Wrangler",2011));
        emptyOpt.orElseThrow(()->new IllegalArgumentException());

    }
}
