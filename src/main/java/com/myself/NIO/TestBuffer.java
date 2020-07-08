package com.myself.NIO;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *  其他方法讲解：
 *      https://blog.csdn.net/u012345283/article/details/38357851
 *      https://blog.csdn.net/Welcome_Word/article/details/79831852
 *
 * 一、Buffer 缓冲区： 在JAVA NIO中负责数据的存取。缓冲区就是数组，用于存储不用数据类型的数据
 *
 * 根据数据类型（boolean 除外），提供相应类型的缓冲区：
 *   ByteBuffer
 *   CharBuffer
 *   ShortBuffer
 *   IntBuffer
 *   LongBuffer
 *   FloatBuffer
 *   DoubleBuffer
 *   上述缓冲区管理方式几乎一直，都是通过 allocate() 获取缓冲区
 *
 * 二、缓冲区的两个核心方法：
 *  put() : 存入数据到缓冲区
 *  get() : 获取缓冲区的数据
 *
 * 三、继承自Buffer 下的核心属性
 *     private int mark = -1;
 *     private int position = 0;   // 位置，表示缓冲区中正在操作的数据的位置
 *     private int limit;       // 界限，表示缓冲区可以操作数据的大小，（limit 后数据不能进行改写）
 *     private int capacity;   //容量，表示缓冲区中最大存储数据的容量，一旦声明，不能改变
 *
 *     mark(): 标记当前位置，rest() 恢复到mark的位置
 *     hasRemaining() : 是否有剩余数据， remaining() 剩余数据的数量
 *     0 <= mark <= position <= limit <= capacity
 *
 *  四、直接缓冲区与非直接缓冲区,直接缓冲区只有bytebuffer支持
 *      allocateDirect() allocate()
 *
 *
 */
public class TestBuffer {


    @Test
    public void test2() {
//        System.out.println(Charset.defaultCharset());
//        System.out.println(new String("测试".getBytes(Charset.forName("UTF-8")),Charset.forName("UTF-8")));
//        char a = 65;
//        System.out.println(a);

        byte[] bytes = "abcde".getBytes();
        // 1.分配一个指定大小缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(bytes);
        buf.flip();
        System.out.println((char)buf.get(0));
        byte [] read = new byte[2];
        buf.get(read);
        System.out.println(new String(read));
        System.out.println(buf.position());
        buf.mark();
        buf.get(read);
        System.out.println(new String(read));
        System.out.println(buf.position());
        buf.reset();
        System.out.println(buf.position());
        if(buf.hasRemaining()){
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void test1() {
//        System.out.println(Charset.defaultCharset());
//        System.out.println(new String("测试".getBytes(Charset.forName("UTF-8")),Charset.forName("UTF-8")));
//        char a = 65;
//        System.out.println(a);

        byte [] bytes = "测试".getBytes();
        // 1.分配一个指定大小缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("容量：capacity ="+buf.capacity());
        System.out.println("界限：limit ="+buf.limit());
        System.out.println("当前位置：position ="+buf.position());
        // 2.利用put放入数据
        System.out.println("--------放入数据-----------");
        buf.put("abcde".getBytes());
        System.out.println("容量：capacity ="+buf.capacity());
        System.out.println("界限：limit ="+buf.limit());
        System.out.println("当前位置：position ="+buf.position());
        System.out.println("-------------------");

        // 3.切换成读取数据的模式  flip() 方法切换
        System.out.println("--------切换为读模式-----------");
        buf.flip();
        System.out.println("容量：capacity ="+buf.capacity());
        System.out.println("界限：limit ="+buf.limit());
        System.out.println("当前位置：position ="+buf.position());
        System.out.println("-------------------");

        // 4.读取数据
        System.out.println("--------读取数据-----------");
        byte [] readBytes = new byte[buf.limit()];
        buf.get(readBytes);
        System.out.println(new String(readBytes));
        System.out.println("容量：capacity ="+buf.capacity());
        System.out.println("界限：limit ="+buf.limit());
        System.out.println("当前位置：position ="+buf.position());
        System.out.println("-------------------");

        // 5.rewind() 可以重复读数据
        buf.rewind();
        System.out.println(new String(readBytes));
        System.out.println("容量：capacity ="+buf.capacity());
        System.out.println("界限：limit ="+buf.limit());
        System.out.println("当前位置：position ="+buf.position());
        System.out.println("-------------------");

        // 5.clear() 清空缓冲区，缓冲区中的数据依然存在，但是是“被遗忘”状态，limit,positon都重置了。
        buf.clear();
        System.out.println(new String(readBytes));
        System.out.println("容量：capacity ="+buf.capacity());
        System.out.println("界限：limit ="+buf.limit());
        System.out.println("当前位置：position ="+buf.position());
        System.out.println("-------------------");

    }
}
