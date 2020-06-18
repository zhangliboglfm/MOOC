package com.myself.JUC;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 *  创建线程的四种方式：
 *          extends Thread
 *          implemnets Runable
 *          implements Callable   有返回值
 *              执行Callable方式，需要FutureTask 实现类的支持，FutureTask 是Future的实现
 *          ExectorService        线程池
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       ThreadDemo td = new ThreadDemo();
        FutureTask<Integer> result = new FutureTask<>(td);
        new Thread(result).start();
        System.out.println(result.get());  // 调用result.get()  会一直等待子线程执行完毕，所以可以用于闭锁
    }
}

class ThreadDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        int sum=0;
        for (int i=0;i<100;i++){
            sum +=i;
        }
        return sum;
    }
}
