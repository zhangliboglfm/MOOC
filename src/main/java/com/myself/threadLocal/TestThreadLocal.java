package com.myself.threadLocal;


/**
 * 并发编程：
 *  https://www.cnblogs.com/dolphin0520/p/3920407.html
 *  深入解析 ThreadLocal
 */
public class TestThreadLocal {

    ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>();

    public static void main(String[] args) {
        TestThreadLocal testThreadLocal = new TestThreadLocal();
        testThreadLocal.stringThreadLocal.set("str");
        System.out.println(testThreadLocal.stringThreadLocal.get());
    }

}
