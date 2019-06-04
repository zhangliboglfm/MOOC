package com.myself.JAVA_8_New_Character;

import com.myself.JAVA_8_New_Character.extraUtil.Car;
import org.apache.coyote.http2.Stream;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * java 1.8 惯用语
 */
public class java8NewCharacter {


    /**
     * 命令式编程：  tell what and how, 例如自己控制for循环遍历list查看是否包含某个元素
     *
     * 声明式编程： tell what, 知道查找list的某个元素，扫描底层jdk库，可以调用contains方法,编程更加简便高效
     *
     * 函数式编程： 声明式编程+调用高级函数
     *
     */
    @Test
    public void functionalStyle(){
        //merge 的方法有三个参数 第一个是所选map的key，第二个是需要合并的值，第三个是 如何合并，也就是说合并方法的具体实现。
        LinkedHashMap<String,Integer> linkedHashMap = new LinkedHashMap<String, Integer>();
        linkedHashMap.merge("key",1,(oldvale,value)->{
            System.out.println("oldvale--->"+oldvale);
            System.out.println("value--->"+value);
            return oldvale+value;
        });
        linkedHashMap.merge("key",1,(oldvale,value)->{
            System.out.println("oldvale--->"+oldvale);
            System.out.println("value--->"+value);
            return oldvale+value;
        });
        linkedHashMap.merge("key",1,(oldvale,value)->{
            System.out.println("oldvale--->"+oldvale);
            System.out.println("value--->"+value);
            return oldvale+value;
        });
        System.out.println(linkedHashMap.get("key"));  //  3

        //compute ,指定的key在map中的值进行操作 不管存不存在
        List<String> keyList = Arrays.asList("name","age","phone");
        for (String string :keyList){
            linkedHashMap.compute(string,(k,v)->{
                if(k!=null){
                    System.out.println("kk--->"+k);
                }
                if(v==null){
                    v=0;
                }
                v+=1;
                return v;
            });
        }

        // computeIfAbsent  若key->"12" 对应的value为空，会将第二个参数的返回值存入并返回
        int key2 = linkedHashMap.computeIfAbsent("ag1", k ->4 );

        // computeIfAbsent  若key->"12" 对应的value为空，会将第二个参数的返回值存入并返回
        int key3 = linkedHashMap.computeIfPresent("ag1", (k,v) -> v+1);
        System.out.println(key3);

    }


    /**
     * 运用集合管道处理数据
     *
     * 获取 2000 年后制造的汽车的名称。然后按年份对这些型号进行升序排
     */
    @Test
    public void collectionPipeline(){
        List<Car> carList =Arrays.asList(
                new Car("Jeep", "Wrangler", 2011),
                new Car("Jeep", "Comanche", 1990),
                new Car("Dodge", "Avenger", 2010),
                new Car("Buick", "Cascada", 2016),
                new Car("Ford", "Focus", 2012),
                new Car("Chevrolet", "Geo Metro", 1992));

        List<String> filterNames=carList.stream().filter(car->car.getYear()>2000)
                .sorted(Comparator.comparing(Car::getYear))
                .map(Car::getModel)
                .collect(Collectors.toList());
        System.out.println(filterNames);
    }


    /**
     * intStream() 流处理类的方法
     *  range()  左闭右开
     *  rangeClosed()  左闭右闭
     *  IntStream.iterate(1, e -> e + 3).limit(34)  跳过值，limit限制iterate的迭代次数
     *  IntStream.iterate(7, e -> e - 1).limit(7)  逆向迭代
     */
    @Test
    public void streamMethods(){
        IntStream.range(1,4)
                .forEach(i-> System.out.println(i));

        // java 创建四种线程池：https://www.cnblogs.com/zhaoyan001/p/7049627.html
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0,5)
                .forEach(i->
                        executorService.submit(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Running task "+i);
                            }
                        })
                        );
        executorService.shutdown();
        List<String> names = Arrays.asList("Jack", "Jill", "Nate", "Kara", "Kim", "Jullie", "Paul", "Peter");

        System.out.println(
                names.stream()
                        .filter(name -> name.length() == 4)
                        .collect(Collectors.joining(", ")));

        // lambda 通过方法引用传递参数
        // .forEach(System.out::println);
        // (this::incerment)
        // (Ojbect::toString)
        // (Ojbect::new)

        List<Integer> integerList = Arrays.asList(1,2,3);
        LinkedList<Integer> linkedList =integerList.stream().collect(Collectors.toCollection(LinkedList<Integer>::new));

        // lambda 传递多个参数  https://www.ibm.com/developerworks/cn/java/j-java8idioms5/index.html?ca=drs-
        //  .reduce(0, (total, e) -> Integer.sum(total, e)));  -->  .reduce(0, Integer::sum));
        //  转换的条件： var1,var2  --->  ClassName.menthodName(var1,var2) 该方法传递两个参数，并且按照完全相同的顺序
        // 第一次  total为初始值 0  e为1
        // 第二次  total为0+1=1  e为2
        // 第三次  total为1+2=3  e为3  返回 3+3=6

        // .reduce("", (result, letter) -> result.concat(letter)));  -->  .reduce("", String::concat));
        // 转换的条件：var1,var2  ---> var1.methodName(var2)  使用第一个形参作为目标方法的引用

        System.out.println(integerList.stream().reduce(0,(total,e)->{
            System.out.println("tatal--"+total);
            System.out.println("ee--"+e);
            return Integer.sum(total,e);
        }));
        System.out.println(integerList.stream().reduce(0,Integer::sum));

    }



}
