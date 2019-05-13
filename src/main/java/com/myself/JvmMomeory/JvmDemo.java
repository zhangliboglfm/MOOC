package com.myself.JvmMomeory;

public class JvmDemo {

    public static void main(String[] args) {
        System.out.println(mb(Runtime.getRuntime().maxMemory()));
        System.out.println(mb(Runtime.getRuntime().freeMemory()));
        System.out.println(mb(Runtime.getRuntime().totalMemory()));
    }

    static String mb(long s) {
        return String.format("%d (%.2f M)", s, (double) s / (1024 * 1024));
    }
}
