package com.myself.JAVA_8_New_Character;

import com.aliyun.mns.sample.Sample;
import com.myself.JAVA_8_New_Character.extraUtil.Car;
import org.apache.coyote.http2.Stream;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

        // computeIfAbsent  若key 对应的value为空，会将第二个参数的返回值存入并返回
        int key2 = linkedHashMap.computeIfAbsent("ag1", k ->4 );

        // computeIfAbsent  若key 对应的value不为空，会将第二个参数的返回值存入并返回
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


    /**
     * 函数式接口    https://www.ibm.com/developerworks/cn/java/j-java8idioms7/index.html?ca=drs-
     * 函数接口的3条重要规则：
     *      1.一个函数接口只有一个抽象方法。
     *      2.在 Object 类中属于公共方法的抽象方法不会被视为单一抽象方法。
     *      3.函数接口可以有默认方法和静态方法。
     *
     */
    @Test
    public void  functionalInterface(){

        /*Thread thread =new Thread(()-> System.out.println(123));
        thread.start();*/

        new java8NewCharacter().borrowCar(car -> System.out.println("using " + car));
    }

    public void borrowCar(Consumer<Car> carConsumer){
        Car car = new Car("Jeep", "Wrangler", 2011);
        System.out.println(car);
        try {
            carConsumer.accept(car);
        }finally {
            System.out.println(car);
        }
    }

    /**
     * https://www.ibm.com/developerworks/cn/java/j-java8idioms8/index.html?ca=drs-
     * createComparator1  createComparator2  java 1.8 类型推断的局限性
     * @return
     */
    public static Comparator<Car> createComparator1(){
        // 根据返回值得类型来推断car的类型
        return Comparator.comparing(car -> car.getYear());
    }
    public static Comparator<Car> createComparator2(){
        //推断不出 car的类型
//        return Comparator.comparing(car -> car.getYear()).reversed();
        //引入显示类型来确定参数类型
//        return Comparator.comparing((Car car) -> car.getYear()).reversed();
        // 使用方法引用
        return Comparator.comparing(Car::getYear).reversed();
    }

    /**
     *   https://www.ibm.com/developerworks/cn/java/j-java8idioms9/index.html?ca=drs-
     *   级联lambda表达式
     *   IntStream() 应用.collect() 需要调用boxed() 去自动装箱
     */
    static Function<Integer, Predicate<Integer>> isGreaterThan = (Integer pivot) -> {
        Predicate<Integer> isGreaterThanPivot = (Integer candidate) -> {
            return candidate > pivot;
        };
        return isGreaterThanPivot;
    };

    //第一次简化  删除类型细节
    static Function<Integer, Predicate<Integer>> isGreaterThan1 =  pivot -> {
        Predicate<Integer> isGreaterThanPivot = candidate -> {
            return candidate > pivot;
        };
        return isGreaterThanPivot;
    };
    //第二次简化  删除多余的()以及参数引用
    static Function<Integer, Predicate<Integer>> isGreaterThan2 =  pivot -> {
        return  candidate -> {
            return candidate > pivot;
        };
    };
    //第三次简化  删除{}和return 变成级联lambda表达式
    static Function<Integer, Predicate<Integer>> isGreaterThan3 =  pivot -> {
        return  candidate ->candidate > pivot;
    };


    public void testHiger(){
        List<Integer> valuesOver25 = Arrays.asList(1,2,3,4,6,10).stream()
                .filter(isGreaterThan.apply(5))
                .collect(Collectors.toList());
    }

    @Test
    public  void testBoxed() {
        List<Integer> valuesOver25 = IntStream.range(1,50)
                .filter(num->num>10)
                .boxed()
                .collect(Collectors.toList());
    }


    /**
     * https://www.ibm.com/developerworks/cn/java/j-java8idioms10/index.html?ca=drs-
     * 使用闭包捕获状态
     *  lambda 表达式，
     */
    @Test
    public  void print() {
        String location = "World";

        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("Hello " + location);
            }
        };

        runnable.run();

        Runnable runnable1 = ()-> System.out.println("Hello " + location);
        runnable1.run();
    }

    public static void call(Runnable runnable) {
        System.out.println("calling runnable");
        //level 2 of stack
        runnable.run();
    }

    public static void main(String[] args) {
        int value = 4;  //level 1 of stack
        call(
                () -> System.out.println(value) //level 3 of stack
        );


        Runnable runnable = create();

        System.out.println("In main");
        runnable.run();

    }

    public static Runnable create() {
        int value = 4;
        Runnable runnable = () -> System.out.println(value);

        System.out.println("exiting create");
        return runnable;
    }


    /**
     * 函数的纯度规则：
     *      1.函数不会更改任何元素。
     *      2.函数不依赖于任何可能更改的元素。
     */
    @Test
    public void getList(){
        System.out.println(handlList(Arrays.asList(1,3,5,6,8,9),10));

        List<Integer> numbers = Arrays.asList(2, 5, 8, 15, 12, 19, 50, 23);
        System.out.println(
                numbers.stream()
                        .filter(e -> e > 10)
                        .filter(e -> e % 2 == 0)
                        .map(e -> e * 2)
                        .findFirst()
                        .map(e -> "The value is " + e)
                        .orElse("No value found"));

        //peek 方法对调试很有用，使我们能在执行期间留意到Stream
        //该代码处理了直到 12（包含 12）的所有值，但它没有触及超过目标值的任何值。
        // 这是因为最终操作 findFirst 会触发流处理的终止。此外，两个 filter 和 map 调用中的操作融合在一起，
        // 然后在序列中的每个元素上执行计算。超过 findFirst 中的内部终止信号后，就不会再计算元素。
        System.out.println(
                numbers.stream()
                        .peek(e -> System.out.println("processing " + e))
                        .filter(e -> e > 10)
                        .filter(e -> e % 2 == 0)
                        .map(e -> e * 2)
                        .findFirst()
                        .map(e -> "The value is " + e)
                        .orElse("No value found"));
    }

    public List<Integer> handlList(List<Integer> list,int multi){

        return list.stream().filter(num->num>=3)
                .map(num->num*multi)
                .collect(Collectors.toList());
    }
}
