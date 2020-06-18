package com.myself.JUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  用于解决多线程安全的方式：
 *
 *    synchorized: 隐式锁
 *      1. 同步代码块
 *      2. 同步方法
 *
 *    jdk1.5后：
 *      同步锁  LOCK  显示锁
 *      通过lock() 方法上锁，通过unlock() 方法解锁   unlock 放在finally代码块中。
 *
 */
public class TestLock {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket,"1号窗口").start();
        new Thread(ticket,"2号窗口").start();
        new Thread(ticket,"3号窗口").start();
    }
}

class Ticket implements Runnable{

    private int  tick =100;

    private Lock lock = new ReentrantLock();
    @Override
    public void run() {

        while (true){
//            synchronized (this){

            lock.lock();
            try {
                if (tick>0){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" 完成售票，余票为："+ --tick);
                }
            }finally {
                lock.unlock();
            }

//            }
        }

    }
}
