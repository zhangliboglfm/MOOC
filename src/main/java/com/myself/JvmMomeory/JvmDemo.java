package com.myself.JvmMomeory;

import java.io.IOException;

public class JvmDemo {

    public static void main(String[] args) {
        try {
            System.in.read();
        }catch (IOException e){

        }
        // https://www.cnblogs.com/a-small-lyf/p/10280091.html
        //为JVM的最大可用内存，可通过-Xmx设置，默认值为物理内存的1/4，设值不能高于计算机物理内存；
        System.out.println(mb(Runtime.getRuntime().maxMemory()));
        //为当前JVM占用的内存总数，其值相当于当前JVM已使用的内存及freeMemory()的总和，会随着JVM使用内存的增加而增加；
        System.out.println(mb(Runtime.getRuntime().freeMemory()));
        //为当前JVM空闲内存，因为JVM只有在需要内存时才占用物理内存使用，所以freeMemory()的值一般情况下都很小，
        // 而 JVM实际可用内存并不等于freeMemory()，而应该等于maxMemory()-totalMemory()+freeMemory()。
        System.out.println(mb(Runtime.getRuntime().totalMemory()));
    }

    static String mb(long s) {
        return String.format("%d (%.2f M)", s, (double) s / (1024 * 1024));
    }
}
