package com.myself.computerThinking.Chapter1;

/**
 * 产品组合问题
 *  假设有n种产品，不同品种的产品组合在一起销售，假定暂不考虑数量问题，仅考虑品种组合，
 *  即从n中产品中取出k（k<=n）种产品，共有多少种组合方法
 *   C(n,k)=A(n,k)/k!
 *
 *   递归计算：
 *     把原来的n中产品看成是n-1中产品再增加一种产品，可以考虑两种情况：
 *      ①：如果新增产品已经包含在组合中，则只需从原来的n-1种产品中抽出k-1种，即等价于从n中产品中抽出k种进行组合的结果，
 *          此为原问题的（n-1,k-1)问题
 *      ②：如果新增产品不再包含的组合中，则只需从原来的n-1中产品中抽出k中进行组合，此为原问题的（n-1,k)规模问题。
 */
public class combination {

    public static void main(String[] args) {
        System.out.println(combination(4,2));
    }

    public  static int combination(int n,int k){
        if(n<k){
            return 0;
        }else if(k==0){
            return 1;
        }else if(k==n){
            return 1;
        }else {
            return combination(n-1,k-1)+combination(n-1,k);
        }
    }
}
