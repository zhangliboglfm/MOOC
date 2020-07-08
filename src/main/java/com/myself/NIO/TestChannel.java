package com.myself.NIO;

import com.aliyun.openservices.shade.io.netty.buffer.ByteBuf;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

/**
 *  通道(Channel)
 *  一、用于源节点与目标节点的连接。在Java NIO 中负责缓冲区中数据的传输。 Channel 本身不存储数据，因此需要配合缓冲区进行传输
 *
 *  二、通道的主要实现
 *     java.nio.channels.Channel 接口：
 *          |--FileChannel   : 用于读取、写入、映射和操作文件的通道
 *          |--SocketChannel : 通过TCP 读写网络中的数据
 *          |--ServerSocketChannel  ：可以监听新进来的TCP 连接，对每一个新进来的连接都会创建一个 SocketChannel
 *          |--DatagramChannel      ：通过UDP 读写网络中的数据
 *
 *  三、
 *      1.获取通道的一种方式是对支持通道的对象的调用 getChannel() 方法。 支持通道的类如下：
 *      FileInputStream、FileOutputSteam、RandomAccessFile
 *      DatagramSocket、Socket、ServerSocket
 *
 *     2. JDK 1.7 对NIO做了较大的改动，统称为 NIO2
 *      在1.7中。获 Files 类的静态方法 newByteChannel() 获取字节通道
 *      在1.7 中，通过通道FileChannel的静态方法 open() 打开并返回指定通道。
 *
 * 四、通道之间的数据传输 （直接缓冲区的方式）
 *   transferFrom()
 *   transferTo()
 *
 *  五、分散(Scatter) 和 聚集 (Gather)
 *       分散写入( Scatter Reads):将通道中的数据分散到多个缓冲区中,按照缓冲区的顺序，从channel中读取数据，依次将Buffer填满
 *      聚集写入( Gathering Writers)：是指将多个 Buffer 中的数据 "聚集" 到 Channel, 按照缓冲区的顺序，写入 position 和 limit 之间的数据到 Channel。
 *
 *  六、字符集 Charset
 *   编码：字符串 -> 字节数组
 *   解码：字节数组 -> 字符串
 */
public class TestChannel {

    // 字符集   GBK 一个汉字占两个字节固定的， UTF-8, 变长的，对于英文比较多的论坛 ，使用GBK则每个字符占用2个字节，而使用UTF－8英文却只占一个字节。
    // 对于中文较多的论坛，适宜用GBK编码节省数据库空间。
    //对于英文较多的论坛，适宜用UTF-8节省数据库空间。
    @Test
    public void test6() throws IOException {
        // 出来 ByteBuffer 和 CharBuffer 之间的转换
        Charset cs1 = Charset.forName("utf-8");

        // 获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        // 获取解码器
        CharsetDecoder cd  = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("测试编码转换");
        cBuf.flip();
        // 编码
        ByteBuffer bBuf = ce.encode(cBuf);
        for (int i=0;i<bBuf.limit();i++){
            System.out.println(bBuf.get());
        }

        bBuf.flip();
        // 解码
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());


    }

    @Test
    public void test5(){
        Map<String, Charset> map = Charset.availableCharsets();
        for(Map.Entry<String,Charset> entry:map.entrySet()){
            System.out.println(entry.getKey() +"="+entry.getValue());
        }

    }

    @Test
    public void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt","rw");

        // 1.获取通道
        FileChannel channel1 = raf1.getChannel();

        //2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3.分散读取
        ByteBuffer [] bufs = {buf1,buf2};
        channel1.read(bufs);
        for (ByteBuffer buf:bufs){
            buf.flip();
        }
        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("-------我是分割线--------");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

        // 4.聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt","rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }



    // 通道之间的数据传输(直接缓冲区的方式)
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

        inChannel.transferTo(0,inChannel.size(),outChannel);
        outChannel.transferFrom(inChannel,0,inChannel.size());
        inChannel.close();
        outChannel.close();
    }


    // 2.利用直接缓冲区完成文件的复制（内存映射文件）
    // 有时候复制完成，但是引用对象没有释放，会一直占用物理内存，造成 JVM 卡住磁盘，不稳定
    @Test
    public void test2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

        // 内存映射文件，和 allocateDirect() 一样
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());

        // 直接对缓冲区进行数据的读写操作，不需要通道
        byte [] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);
        inChannel.close();
        outChannel.close();
    }

    // 1. 利用通道完成文件的复制
    @Test
    public void test1() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel  inChannel=null;
        FileChannel  outChannel=null;
        try {
            fis = new FileInputStream("E:\\applog\\xms\\info\\log-info-2020-07-06-0.log");
            fos = new FileOutputStream("E:\\applog\\xms\\info\\log-info-2020-07-06-1.log");

            // 1.获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            // 2. 分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 3. 将通道中的数据存入缓冲区
            while (inChannel.read(buf)!=-1){
                // 4. 将缓冲区中的数据写入通道
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
