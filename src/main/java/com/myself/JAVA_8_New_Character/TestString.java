package com.myself.JAVA_8_New_Character;

import org.junit.Test;

import java.util.*;

/**
 *  StringBuffer，StringBulider，String :https://blog.csdn.net/Alpha_Paser/article/details/89352122
 */
public class TestString {


    /**
     * 情景一：字符串池
     * JAVA虚拟机(JVM)中存在着一个字符串池，其中保存着很多String对象;
     * 并且可以被共享使用，因此它提高了效率。
     * 由于String类是final的，它的值一经创建就不可改变。
     * 字符串池由String类维护，我们可以调用intern()方法来访问字符串池。
     */
    @Test
    public void test1(){
        String s1 = "abc";
        //↑ 在字符串池创建了一个对象
        String s2 = "abc";
        //↑ 字符串pool已经存在对象“abc”(共享),所以创建0个对象，累计创建一个对象
        System.out.println("s1 == s2 : "+(s1==s2));
        //↑ true 指向同一个对象，
        System.out.println("s1.equals(s2) : " + (s1.equals(s2)));
        //↑ true  值相等
    }

    /**
     * 情景二：关于new String("")
     *
     */
    @Test
    public void test2(){
        String s1 = "abc";
        String s3 = new String("abc");
        //↑ 创建了两个对象，一个存放在字符串池中，一个存在与堆区中；
        //↑ 还有一个对象引用s3存放在栈中
        String s4 = new String("abc");
        //↑ 字符串池中已经存在“abc”对象，所以只在堆中创建了一个对象
        System.out.println("s3 == s4 : "+(s3==s4));
        //↑false   s3和s4栈区的地址不同，指向堆区的不同地址；
        System.out.println("s3.equals(s4) : "+(s3.equals(s4)));
        //↑true  s3和s4的值相同
        System.out.println("s1 == s3 : "+(s1==s3));
        //↑false 存放的地区多不同，一个栈区，一个堆区
        System.out.println("s1.equals(s3) : "+(s1.equals(s3)));
        //↑true  值相同
    }

    /**
     * 情景三：
     * 由于常量的值在编译的时候就被确定(优化)了。
     * 在这里，"ab"和"cd"都是常量，因此变量str3的值在编译时就可以确定。
     * 这行代码编译后的效果等同于： String str3 = "abcd";
     */
    @Test
    public void test3(){
        String str1 = "ab" + "cd";  //1个对象
        String str11 = "abcd";
        System.out.println("str1 = str11 : "+ (str1 == str11));
    }


    /**
     * 情景四：
     * 局部变量str2,str3存储的是存储两个拘留字符串对象(intern字符串对象)的地址。
     *
     * 第三行代码原理(str2+str3)：
     * 运行期JVM首先会在堆中创建一个StringBuilder类，
     * 同时用str2指向的拘留字符串对象完成初始化，
     * 然后调用append方法完成对str3所指向的拘留字符串的合并，
     * 接着调用StringBuilder的toString()方法在堆中创建一个String对象，
     * 最后将刚生成的String对象的堆地址存放在局部变量str3中。
     *
     * 而str5存储的是字符串池中"abcd"所对应的拘留字符串对象的地址。
     * str4与str5地址当然不一样了。
     *
     * 内存中实际上有五个字符串对象：
     *       三个拘留字符串对象、一个String对象和一个StringBuilder对象。
     */
    @Test
    public void test4(){
        String str2 = "ab";  //1个对象
        String str3 = "cd";  //1个对象
        String str4 = str2+str3;
        String str5 = "abcd";
        System.out.println("str4 = str5 : " + (str4==str5)); // false
    }

    /**
     * 情景五：
     *  JAVA编译器对string + 基本类型/常量 是当成常量表达式直接求值来优化的。
     *  运行期的两个string相加，会产生新的对象的，存储在堆(heap)中
     */
    @Test
    public void test5(){
        String str6 = "b";
        String str7 = "a" + str6;
        String str67 = "ab";
        System.out.println("str7 = str67 : "+ (str7 == str67));
        //↑false str6为变量，在运行期才会被解析。
        final String str8 = "b";
        String str9 = "a" + str8;
        String str89 = "ab";
        System.out.println("str9 = str89 : "+ (str9 == str89));
        //↑true str8为常量变量，编译期会被优化
        String str678 = "a"+"b";
        System.out.println(str67==str678);

        // https://blog.csdn.net/wzdoxu/article/details/83293548
        //↑ true
        String a = "a1";
        String b = "a" + 1;
        System.out.println((a == b)); //result = true

        String a1 = "atrue";
        String b1 = "a" + true;
        System.out.println((a1 == b1)); //result = true

        String a2 = "a3.4";
        String b2 = "a" + 3.4;
        System.out.println((a2 == b2)); //result = true

        String a3 = "ab";
        final String bb = getBB();
        String b3 = "a" + bb;
        System.out.println((a3 == b3)); //result = false

    }
    private static String getBB() {
        return "b";
    }
    @Test
    public void test6(){
        Integer i1 = Integer.valueOf(10);
        Integer i2 = Integer.valueOf("10");
        Integer i3 = 10;
        System.out.println(i1==i2);
        System.out.println(i1==i3);

    }

    @Test
    public void test7(){
        ArrayList arrayList =new ArrayList<>();
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.remove(1);
        ArrayList removeList = new ArrayList();
        removeList.add(0);
        removeList.add(1);
        arrayList.addAll(removeList);
        System.out.println(arrayList);

    }

    @Test
    public void test8(){
        Stack stack = new Stack();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        System.out.println(stack.peek());
    }

}
