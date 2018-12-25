package com.myself.computerThinking.Chapter1;

import java.util.Iterator;
import java.util.Stack;

/**
 * 汉诺塔问题
 *
 *  假设有10个盘子，柱子 a,b,c
 *  已经把前9个盘子移动到了b上面，那么只需把10号盘移动到c上，
 *  再考虑现在有9个盘子在b上，通过a的辅助，把9好盘移动到c上面，一次类推。。。
 *
 */
public class HanoiTower {
    public static void print(Stack<Integer> s) {
        Iterator<Integer> i = s.iterator();
        while (i.hasNext()) {
            System.out.printf("%d ", i.next());
        }
        System.out.println();
    }

    public static void resolve(int n, Stack<Integer> a, Stack<Integer> b, Stack<Integer> c) {
        if (n==0) return;
        resolve(n-1, a, c, b);
        c.push(a.pop());
        resolve(n-1, b, a, c);
    }

    public static void main(String[] args) {
        int count = 32;
        Stack<Integer> a = new Stack<Integer>();
        Stack<Integer> b = new Stack<Integer>();
        Stack<Integer> c = new Stack<Integer>();

        for (int i=count; i>0; i--) {
            a.push(i);
        }

        print(a);
        long start = System.currentTimeMillis();
        resolve(count, a, b, c);
        long end = System.currentTimeMillis();
        print(c);

        System.out.println((end - start)/1000);
    }
}
