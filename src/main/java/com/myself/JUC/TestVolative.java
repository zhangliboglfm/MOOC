package com.myself.JUC;

import lombok.SneakyThrows;

/**
 *  内存可见性问题:
 *      JVM 为每个线程分配独立的缓存来存储数据。当多个线程操作共享数据时，彼此不可见
 *      1. 运行时，主内存中的 flag = false;
 *      2. 子线程先运行，此时 copy一份到自己的内存中， flag=false,
 *          等待200ms之后，修改自己内存中flag =true,并写回主内存。
 *
 *      3. main线程 copy一份到自己内存中，此时 flag = false, 子线程还没有把修改的值写回主内存，
 *         所以flag = false, 陷入死循环。
 *
 *  解决方法：
 *       1.  同步锁 sychnorized(td) 强制去主存刷新。  效率太慢
 *       2.  volatile  当多个线程操作共享数据时，保证内存中的数据可见。   效率低，但是对锁效率高
 *             相较于 synchronized 是一种较轻量级的同步策略。
 *
 *  注意：
 *       1.voliate 不具备 "互斥性"，
 *       2. 不能保证变量的 "原子性"
 *
 *
 */
public class TestVolative {

    public static void main(String[] args)  {

        ThreadDemo td = new ThreadDemo();
        td.start();
        while (true){
//            System.out.println("123");
//            synchronized (td){
                if(td.isFlag()){
                    break;
//                }
            }

        }
    }


    static class  ThreadDemo extends Thread{
        private volatile boolean flag = false;

        @Override
        public void run() {
            try {
                Thread.sleep(200);
            }catch (Exception e){
                e.printStackTrace();
            }
            flag =true;
            System.out.println("thread-0 get  flag =:"+flag);
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }

}
