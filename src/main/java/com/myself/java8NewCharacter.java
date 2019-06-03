package com.myself;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * java 1.8 惯用语
 */
public class java8NewCharacter {


    /**
     * 命令是编程：  tell what and how, 例如自己控制for循环遍历list查看是否包含某个元素
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
}
