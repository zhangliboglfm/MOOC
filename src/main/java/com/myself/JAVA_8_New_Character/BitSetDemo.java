package com.myself.JAVA_8_New_Character;

import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;

/**
 * BitSet的使用
 *
 *  基础讲解：https://blog.csdn.net/haojun186/article/details/8482343
 *
 *  使用场景：https://my.oschina.net/cloudcoder/blog/294810
 *
 *
 */
public class BitSetDemo {


    /**
     *  https://blog.csdn.net/blog_szhao/article/details/23997881
     *  移位运算：  <<, >>,>>>
     *      >> 表示右移，如果该数为正，则高位补0，若为负数，则高位补1         除以2*n
     *      >>>表示无符号右移，也叫逻辑右移，即若该数为正，则高位补0，而若该数为负数，则右移后高位同样补0。
     *      << 表示左移，低位补0                                乘以2*n
     *      左移没有<<<运算符
     *
     *
     *      移位操作：>> 和 <<
     *          int 数据类型移动 num位 == (num % 32)位
     *          double 数据类型移动 num位 == (num % 64)位
     */
    @Test
    public void shiftOperation(){

        System.out.println(Long.toBinaryString(0x8000000000000000L));
        getMask(0);
        getMask(1);
        getMask(2);
        System.out.println(Long.toBinaryString(0xFFFFFFFFFFFFFFFFL));
        System.out.println(Long.toBinaryString(0xFFFFFFFFFFFFFFFFL));
        int num =64;
        printInfo(num<<2);
        printInfo(num>>2);
        printInfo(num>>>2);
    }
    private void printInfo(int num){
        System.out.println(Integer.toBinaryString(num));
    }


    private long getMask(int index){
        long rootMask =0xFFFFFFFFFFFFFFFFL;
        long rootMask1=rootMask>>>(index);
        rootMask1 &= rootMask<<(64-index-1);
        System.out.println(Long.toBinaryString(rootMask1));
        return rootMask;
    }


    /**
     * 求一个字符串包含的char
     *
     */
    @Test
    public  void containChars() {
        String str="abc";
        BitSet used = new BitSet();
        for (int i = 0; i < str.length(); i++)
            used.set(str.charAt(i)); // set bit for char

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int size = used.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            if (used.get(i)) {
                sb.append((char) i);
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    /**
     * 求素数 有无限个。一个大于1的自然数，如果除了1和它本身外，不能被其他自然数整除(除0以外）的数称之为素数(质数） 否则称为合数
     */
    @Test
    public  void computePrime() {
        BitSet sieve = new BitSet(1024);
        int size = sieve.size();
        for (int i = 2; i < size; i++)
            sieve.set(i);
        int finalBit = (int) Math.sqrt(sieve.size());

        for (int i = 2; i < finalBit; i++)
            if (sieve.get(i))
                for (int j = 2 * i; j < size; j += i)
                    sieve.clear(j);

        int counter = 0;
        for (int i = 1; i < size; i++) {
            if (sieve.get(i)) {
                System.out.printf("%5d", i);
                if (++counter % 15 == 0)
                    System.out.println();
            }
        }
        System.out.println();
    }

    /**
     * 进行数字排序
     */
    @Test
    public  void sortArray() {
        int[] array = new int[] { 423, 700, 9999, 2323, 356, 6400, 1,2,3,2,2,2,2 };
        BitSet bitSet = new BitSet(2 << 13);
        // 虽然可以自动扩容，但尽量在构造时指定估算大小,默认为64
        System.out.println("BitSet size: " + bitSet.size());

        for (int i = 0; i < array.length; i++) {
            bitSet.set(array[i]);
        }
        //剔除重复数字后的元素个数
        int bitLen=bitSet.cardinality();

        //进行排序，即把bit为true的元素复制到另一个数组
        int[] orderedArray = new int[bitLen];
        int k = 0;
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            orderedArray[k++] = i;
        }

        System.out.println("After ordering: ");
        for (int i = 0; i < bitLen; i++) {
            System.out.print(orderedArray[i] + "\t");
        }

        System.out.println("iterate over the true bits in a BitSet");
        //或直接迭代BitSet中bit为true的元素iterate over the true bits in a BitSet
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.print(i+"\t");
        }
        System.out.println("---------------------------");
    }

    /**
     * 将BitSet对象转化为ByteArray
     * @return
     */
    @Test
    public void  bitSet2ByteArray() {
        BitSet bitSet = new BitSet();
        bitSet.set(3, true);
        bitSet.set(98, true);
        byte[] bytes = new byte[bitSet.size() / 8];
        for (int i = 0; i < bitSet.size(); i++) {
            int index = i / 8;
            int offset = 7 - i % 8;
            bytes[index] |= (bitSet.get(i) ? 1 : 0) << offset;
        }
        System.out.println(bytes);
    }

    /**
     * 将ByteArray对象转化为BitSet
     * @param bytes
     * @return
     */
    public static BitSet byteArray2BitSet(byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bitSet.set(index++, (bytes[i] & (1 << j)) >> j == 1 ? true
                        : false);
            }
        }
        return bitSet;
    }

    /**
     * 获取对应位的掩码  clear(int bitIndex)  mask;
     */
    @Test
    public void testBitClear(){

        System.out.println(Long.toBinaryString(3) );
        System.out.println(Long.toBinaryString((3l << 65)) );
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-1<<32));
    }
}

