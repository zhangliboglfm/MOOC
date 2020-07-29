package com.myself.JUC;


import java.util.concurrent.locks.LockSupport;

/**
 *  线程的中断，阻塞
 *      关于interrupt(),interrupted(),isInterrupted()用法分析  :https://blog.csdn.net/qq_34115899/article/details/81114969
 *  LockSupport park()和unpark() : https://blog.csdn.net/aitangyong/article/details/38373137
 */
public class Interrupt {

    public static void main(String[] args) throws Exception {
       /*
        // test1
        Thread t = new Thread(new Worker());
        t.start();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        t.interrupt();
        System.out.println("Main thread stopped.");
*/


       /*// test2
        Thread t = new Thread(new Worker1());
        t.start();

        Thread.sleep(100);
        t.interrupt();

        System.out.println("Main thread stopped.");
*/

      /*  // test3
        Thread t = new Thread(new Worker2());
        t.start();
        Thread.sleep(2000);
        // 中断线程
        t.interrupt();
        */

      // test4
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
        System.out.println("A");
        LockSupport.park();
        System.out.println("B");
        LockSupport.park();
        System.out.println("C");

    }


    public static class Worker2 implements Runnable {
        private int count = 0;
        public void run() {
            long start = System.currentTimeMillis();
            long end = 0;

            while ((end - start) <= 1000)
            {
                count++;
                end = System.currentTimeMillis();
            }

            System.out.println("after 1 second.count=" + count);

            //等待或许许可
            LockSupport.park();
            System.out.println("thread over." + Thread.currentThread().isInterrupted());
        }
    }


    public static class Worker1 implements Runnable {
        public void run() {
            System.out.println("Worker started.");
            try {
                Thread.sleep(500); // 此时被interrupt()会抛出InterruptedException
            } catch (InterruptedException e) {
                Thread thread = Thread.currentThread();
                System.out.println("再次中断之前isInterrupted():" + thread.isInterrupted());
                System.out.println("再次中断之前interrupted():" + Thread.interrupted());
                // 再次调用interrupt方法中断自己，将中断状态设置为“中断”
                thread.interrupt();
                System.out.println("再次interrupt()后isInterrupted():" + thread.isInterrupted());
                System.out.println("再次interrupt()后第一次interrupted()返回:" + Thread.interrupted());// clear status
                // interrupted()判断是否中断，还会清除中断标志位
                System.out.println("interrupted()后此时再判断IsInterrupted: " + thread.isInterrupted());
                System.out.println("---------After Interrupt Status Cleared----------");
                System.out.println("再次interrupt()后第二次interrupted()返回: " + Thread.interrupted());
                System.out.println("此时再判断IsInterrupted: " + thread.isInterrupted());
            }
            System.out.println("Worker stopped.");
        }
    }


    public static class Worker implements Runnable {

        public void run() {
            System.out.println("Worker started.");
            boolean f; // 用于检测interrupted()第一次返回值
            int i = 0;
            Thread c = Thread.currentThread();
            System.out.println("while之前线程中断状态isInterrupted()：" + c.isInterrupted());
            while (!(f = Thread.interrupted())) {// 判断是否中断，如果中断，那么跳出并清除中断标志位
                // 一旦检测到中断，interrupted()第一次返回true，就可以跳出循环，第二次以及以后都是返回false
                System.out.println("while内，还没中断，interrupted()返回值为：" + f);
                System.out.println(c.getName() + "  " + i++ + "  " + c.isInterrupted());
            }
            System.out.println("跳出循环即第一次中断interrupted()返回值：" + f);
            System.out.println("while之后线程中断状态isInterrupted():" + c.isInterrupted()); // 为false，因为interrupt()会清除中断标志位，显示为未中断
            System.out.println("第二次及以后的interrupted()返回值：" + Thread.interrupted());
            c.interrupt();
            System.out.println("再次中断后查询中断状态isInterrupted():" + c.isInterrupted());
            System.out.println("Worker stopped.");
        }
    }

}
