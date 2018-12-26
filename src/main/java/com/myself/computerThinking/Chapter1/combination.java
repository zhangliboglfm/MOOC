package com.myself.computerThinking.Chapter1;


import java.util.Arrays;
import java.util.Scanner;

public class combination {

    public static void main(String[] args) {
        System.out.println(combination(4,2));

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入要进行全排列的字符串:");
        String s=sc.nextLine();
        Permutation(s.toCharArray());
        sc.close();


    }


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


    /**
     * 给一个字符串，然后把这个字符串重新排列组合，列出所有可能
     */
    public static void Permutation(char[] str){
        if(str==null)
            return;
        int begin=0;
        Permutation(str, begin);
    }
    public static void Permutation(char[] str,int begin){
        if(begin+1==str.length){//遍历完一遍字符串，打印输出一次，返回
            //System.out.println(String.valueOf(str));
            System.out.println(Arrays.toString(str));
        }
        else{
            for(int i=begin;i<str.length;i++){
                if(notSame(str,begin,i)){//去重判断，如果当前字符和后边的字符一样，或者后边字符在之前出现过，不交换。第一次自己和自己换除外。
                    //1,可能出现在第一个位置的字符，即交换第一个字符和后边所有字符，第一次是自己和自己交换
                    swap(str,begin,i);
                    //2,固定第一个字符，递归求后面所有字符的排列
                    Permutation(str, begin+1);
                    //3,递归处理完后边的字符后，记得把前边交换的字符再换回来，保证第一个位置的字符不重复
                    swap(str,begin,i);
                }
            }
        }
    }
    public static boolean notSame(char[] str,int begin,int end){
        for(int j=begin;j<end;j++){
            if(str[j]==str[end]){
                return false;
            }
        }
        return true;
    }
    public static void swap(char[] str,int a,int b){
        char temp = str[a];
        str[a] = str[b];
        str[b] = temp;
    }

}


