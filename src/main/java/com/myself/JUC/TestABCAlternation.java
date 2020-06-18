package com.myself.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  线程按序交替
 *
 *  开启三个线程，三个线程 ID 分别是 A、B、C，每个线程将自己ID打印10遍，要求必须是 ABCABC....
 */
public class TestABCAlternation {

    // 当前正在执行的线程
    private int number =1;
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    public void loopA(){

      try {
          lock.lock();
          // 判断number
          if(number!=1){
              try {
                  conditionA.await();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
          System.out.println(Thread.currentThread().getName());
          number =2;
          conditionB.signal();
      }finally {
          lock.unlock();
      }

    }
    public void loopB(){
        try {
            lock.lock();
            // 判断number
            if(number!=2){
                try {
                    conditionB.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName());
            number =3;
            conditionC.signal();
        }finally {
            lock.unlock();
        }

    }

    public void loopC(){
        try {
            lock.lock();
            // 判断number
            if(number!=3){
                try {
                    conditionC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName());
            number =1;
            conditionA.signal();
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        TestABCAlternation t = new TestABCAlternation();
        new Thread(new Runnable() {
            @Override
            public void run() {
             for (int i=0;i<10;i++){
                t.loopA();
             }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
             for (int i=0;i<10;i++){
                t.loopB();
             }
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
             for (int i=0;i<10;i++){
                t.loopC();
             }
            }
        },"C").start();

        Runnable runnable = ()-> System.out.println(123);

    }
}


