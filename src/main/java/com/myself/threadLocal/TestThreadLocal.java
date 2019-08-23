package com.myself.threadLocal;


/**
 * 并发编程：
 * 1. https://www.cnblogs.com/dolphin0520/p/3920407.html
 *  深入解析 ThreadLocal
 *
 * 2. 线程池存储：https://www.cnblogs.com/qifenghao/p/8977378.html
 *
 * 3. 防止内存泄露：
 *      https://blog.csdn.net/yanluandai1985/article/details/82590336
 */
public class TestThreadLocal {

    ThreadLocal<String> stringThreadLocal = new ThreadLocal<String>();

    public static void main(String[] args) {
        TestThreadLocal testThreadLocal = new TestThreadLocal();
        testThreadLocal.stringThreadLocal.set("str");
        System.out.println(testThreadLocal.stringThreadLocal.get());
    }

}
