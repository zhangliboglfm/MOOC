package com.myself.JUC;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 *  一、 i++ 的原子性： 实际分为三个步骤： "读 - 改 - 写"
 *       int i  = 10;
 *       i++; // 10
 *       int temp =i;
 *       i = i+1;
 *       i = temp;
 *
 *  二、原子变量 jdk1.5之后 java.util.concurrent.atomic 包下提供了常用的原子变量：
 *      1. volatile 保证内存可见性
 *      2. CAS (Compare-And-Swap) 算法保证数据的原子性
 *          CAS 算法是硬件对于并发操作共享数据的支持，包含了三个操作树：
 *              内存值： V
 *              预估值： A
 *              更新值： B
 *              当且仅当 V==A 时，V=B ，否则不做任何操作。
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for(int i=0;i<10;i++){
            new  Thread(ad).start();
        }
    }

    @Test
    public void test(){
        AtomicLongArray atomicLongArray1 = new AtomicLongArray(10);
        System.out.println("数组长度：" + atomicLongArray1.length());
        atomicLongArray1.set(5,100);
        System.out.println("Value: " + atomicLongArray1.get(5));
        System.out.println(atomicLongArray1.getAndAdd(5,10));
        System.out.println("Value: " + atomicLongArray1.get(5));
        System.out.println(atomicLongArray1.compareAndSet(5,100,40));
        System.out.println(atomicLongArray1.compareAndSet(5,110,50));
        System.out.println("结果值:"+ atomicLongArray1.get(5));
    }

    /**
     *  当期望的读数和遍历远远大于列表的更新数时，
     *      CopyOnWriteArrayList 优于 ArrayList，每次添加，都会进行复制，开销非常大。
     */
    @Test
    public void test2(){
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("AA");
        list.add("BB");
        list.add("CC");
        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
            list.add("DD");
        }
        System.out.println(list);
    }

    /**
     * CountDownLatch 闭锁 : 在完成一组正在其他线程中执行的操作之前,他允许一个或多个线程等待
     */
    @Test
    public void test3(){


    }
    @Test
    public void test4(){
        String  s1 = new String("a");
        String s2 = "a";
        System.out.println(s1 == s2);   // false

        String s3 = new String("a") + new String("a");
        s3.intern();
        String s4 = "aa";
        System.out.println(s3 == s4);   // true

    }

}

class AtomicDemo implements Runnable{

    private volatile  int serialNumber =0;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        }catch (Exception e){

        }
        System.out.print(serialNumber++);
        System.out.println(":"+atomicInteger.incrementAndGet());
    }
}