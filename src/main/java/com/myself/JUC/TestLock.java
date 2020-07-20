package com.myself.JUC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * 用于解决多线程安全的方式：
 * <p>
 * synchorized: 隐式锁
 * 1. 同步代码块
 * 2. 同步方法
 * <p>
 * jdk1.5后：
 * 同步锁  LOCK  显示锁
 * 通过lock() 方法上锁，通过unlock() 方法解锁   unlock 放在finally代码块中。
 * <p>
 * ReentranReadWriteLock  && StampedLock :https://www.cnblogs.com/zxporz/p/11642176.html
 */
public class TestLock {
    static ExecutorService service = Executors.newFixedThreadPool(10);
    static StampedLock lock = new StampedLock();
    static long milli = 5000;
    static int count = 0;


    public static void main(String[] args) {

        /*// 测试1
        Ticket ticket = new Ticket();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "3号窗口").start();*/

        long l1 = System.currentTimeMillis();
//        readLock();
        optimisticRead();
        long l2 = writeLock();
        System.out.println(l2 - l1);
        service.shutdown();
    }

    private static long writeLock() {
        long stamp = lock.writeLock(); //获取排他写锁
        count += 1;
        lock.unlockWrite(stamp); //释放写锁
        System.out.println("数据写入完成");
        return System.currentTimeMillis();
    }


    private static void readLock() {//悲观读锁
        service.submit(() -> {
            int currentCount = 0;
            long stamp = lock.readLock(); //获取悲观读锁
            try {
                currentCount = count; //获取变量值
                try {
                    TimeUnit.MILLISECONDS.sleep(milli);//模拟读取需要花费20秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlockRead(stamp); //释放读锁
            }
            System.out.println("readLock==" + currentCount); //显示最新的变量值
        });
        try {
            TimeUnit.MILLISECONDS.sleep(500);//要等一等读锁先获得
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void optimisticRead() {
        service.submit(() -> {
            long stamp = lock.tryOptimisticRead(); //尝试获取乐观读锁
            int currentCount = count; //获取变量值
            if (!lock.validate(stamp)) { //判断count是否进入写模式
                stamp = lock.readLock(); //已经进入写模式，没办法只能老老实实的获取读锁
                try {
                    currentCount = count; //成功获取到读锁，并重新获取最新的变量值
                } finally {
                    lock.unlockRead(stamp); //释放读锁
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(milli);//模拟读取需要花费20秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //走到这里，说明count还没有被写，那么可以不用加读锁，减少了读锁的开销
            System.out.println("optimisticRead==" + currentCount); //显示最新的变量值
        });
        try {
            TimeUnit.MILLISECONDS.sleep(500);//要等一等读锁先获得
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Ticket implements Runnable {

    private int tick = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        while (true) {
//            synchronized (this){

            lock.lock();
            try {
                if (tick > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --tick);
                }
            } finally {
                lock.unlock();
            }

//            }
        }

    }
}
