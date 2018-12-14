package com.myself.computerThinking.Chapter1;


public class DifferenceMethod {
    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            if(differenceMethod(i)!=Math.pow(i,2)){
                System.out.println(i+"   error");
                return;
            }
        }
        System.out.println("congratulation！！");
    }

    /**
     * 差分法计算多项式的和
     *      不同的多项式对应不同的初始值，本例计算n*n
     *                              一阶差分                    二阶差分
     *      n       n*n(平方)       a_n=n*n-(n-1)*(n-1)        b_n =a[n]-a[n-1]
     *      0       0
     *      1       1                 1                         0
     *      2       4                 3                         2
     *      3       9                 5                         2
     *      4       16                7                         2
     *              25
     *      (n+1)*(n+1) = n*n + a[n]+b[n]
     */
    public static int differenceMethod(int n){
        if(n==0){
            return 0;
        }else if(n==1){
            return 1;
        }else if(n==2){
            return 4;
        }
        int square=4,a_n=3,b_n=2,mid,square_before;
        for(int i=2;i<n;i++){
            square_before=square;
            square=square+a_n+b_n;
            mid=a_n;
            a_n=square-square_before;
            b_n=a_n-mid;
        }
        return square;
    }
}