package com.myself.NIO;


/**
 *  阻塞与非阻塞：
 *     ① ：传统的 IO 流都是阻塞式的。也就是说，当一个线程调用 read() 或 write()
 *      时，该线程被阻塞，直到有一些数据被读取或写入，该线程在此期间不
 *      能执行其他任务。因此，在完成网络通信进行 IO 操作时，由于线程会
 *      阻塞，所以服务器端必须为每个客户端都提供一个独立的线程进行处理，
 *      当服务器端需要处理大量客户端时，性能急剧下降。
 *     ② ： Java NIO 是非阻塞模式的。当线程从某通道进行读写数据时，若没有数
 *      据可用时，该线程可以进行其他任务。线程通常将非阻塞 IO 的空闲时
 *      间用于在其他通道上执行 IO 操作，所以单独的线程可以管理多个输入
 *      和输出通道。因此，NIO 可以让服务器端使用一个或有限几个线程来同
 *      时处理连接到服务器端的所有客户端。
 *  选择器：
 *       选择器（Selector） 是 SelectableChannle 对象的多路复用器，Selector 可
 *      以同时监控多个 SelectableChannel 的 IO 状况，也就是说，利用 Selector
 *      可使一个单独的线程管理多个 Channel。Selector 是非阻塞 IO 的核心。
 *
 *      SelectableChannel : 抽象类
 *          |-- AbstractSelectableChannel
 *              |-- SocketChannel
 *              |-- ServerSocketChannel
 *              |-- DatagramChannel
 */

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *  一、使用 NIO 完成网络通信的三个核心, 没有FileChannel,所以FileChannel 无法实现非阻塞式
 *
 *  1、 通道(Channel) ：负责连接
 *          java.nio.channels.Channel 接口:
 *              |-- SelectableChannel
 *                  |-- AbstractSelectableChannel
 *                      |-- SocketChannel
 *                      |-- ServerSocketChannel
 *                      |-- DatagramChannel
 *
 *                      |-- Pipe.SinkChannel
 *                      |-- Pipe.SourceChannel
 *
 *  2、缓冲区(Buffer) ：负责数据的存取
 *
 *  3、选择器(Selector) ：是 SelectableChannel 的多路复用器。用于监控与建通 SelectableChannel 的 IO 状况
 */
public class TestBlockingNIO {


    // 客户端
    @Test
    public void client() throws IOException {
        // 1.获取网络通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

        FileChannel inChannel  = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

        // 2.分配指定大小的缓存池
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 3.读取本地文件
        while (inChannel.read(buf)!=-1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        // 4.告诉服务端，我发送完了数据。
        // 不调用这一句，服务端不能确定客户端有没有发送完数据，会一直处于阻塞状态
        // 解决方法： 调用下面的方法或者切换成非阻塞的模式
        sChannel.shutdownOutput();


        // 5、接收服务端反馈
        int len =0;
        while ((len=sChannel.read(buf))!=-1){
            buf.flip();
            System.out.println(new String(buf.array()));
            buf.clear();
        }
        inChannel.close();
        sChannel.close();
    }

    // 服务端
    @Test
    public void server() throws IOException {
        // 1、获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        // 2、绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        // 3、获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();

        // 4、分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5、接收客户端的数据并保存到本地
        while (sChannel.read(buf)!=-1){
                buf.flip();
                outChannel.write(buf);
                buf.clear();
        }

        // 6、发送反馈到客户端
        buf.put("服务器端收到数据".getBytes());
        buf.flip();
        sChannel.write(buf);
        // 告诉客户端已经发送完了数据。
        sChannel.shutdownOutput();

        //7、关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();
    }

}
