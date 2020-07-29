package com.myself.JUC;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *  线程阻塞： sleep、suspend、join、wait、resume、notify
 *  https://blog.csdn.net/u014659656/article/details/44675063?utm_source=blogxgwz3
 */
public class TestSynchronized {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        System.out.println(dateTime);
        System.out.println(LocalDateTime.now());
    }

}


class Blockable extends Thread {
    private Peeker peeker;
    protected int i;
    public Blockable(){
        peeker = new Peeker(this);
    }

    public synchronized int read(){ return i;}

    public synchronized void update(){
        System.out.println(this.getClass().getName()+"state :i = " + i);
    }

    public void stopPeeker(){
        peeker.terminate();
    }
}

class Peeker extends Thread {
    private Blockable b;
    private int session;
    private boolean stop = false;

    public Peeker(Blockable b){
        this.b = b;
        start();
    }

    public void run(){
        while (!stop) {
            System.out.println(b.getClass().getName()
                    + " Peeker " + (++session)
                    + "; value = " + b.read());
            try {
                 sleep(1000);
                 wait();
            } catch (InterruptedException e){}
        }
    }

    public void terminate() {stop = true;}

}