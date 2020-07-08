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
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Iterator;

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
public class TestNonBlockingNIO {


    // 客户端
    @Test
    public void client() throws IOException {
        // 1.获取网络通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

        // 2.切换成非阻塞模式
        sChannel.configureBlocking(false);

        // 3.分配指定大小的缓存池
        ByteBuffer buf = ByteBuffer.allocate(1024);


        // 4.发送数据到服务器
        buf.put(LocalDateTime.now().toString().getBytes());
        buf.flip();
        sChannel.write(buf);
        sChannel.shutdownOutput();
        buf.clear();
        sChannel.close();
    }

    // 服务端
    @Test
    public void server() throws IOException {
        // 1、获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        // 2.切换为非阻塞模式
        ssChannel.configureBlocking(false);

        // 3、绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        // 4、获取选择器
        Selector selector = Selector.open();

        // 5、将通道注册到选择器上，第二个参数指定监听的事件类型
        // OP_READ :读   OP_WRITE：写  OP_CONNECT：连接  OP_ACCEPT：接收
        // 若注册时不止监听一个事件，则可以使用“位或”操作符连接。
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 6、轮询的方式获取选择器上已经准备就绪的事件
        while (selector.select()>0){

            //7、当前选择器中所有注册的“选择键（以就绪的监听事件）”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()){
                //8、获取准备就绪的事件
                SelectionKey sk = it.next();
                System.out.println("*************");
                //9、判读是什么事件准备就绪
                if(sk.isAcceptable()){
                    // 10、若“连接就绪”，获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    // 11、 切换非阻塞模式
                    sChannel.configureBlocking(false);
                    // 12、将该通道注册到选择器上
                    sChannel.register(selector,SelectionKey.OP_READ);
                    System.out.println("88888888888888888");
                }else if(sk.isReadable()){
                    // 13、获取当前选择器上“读就绪”的通道
                   SocketChannel sChannel= (SocketChannel) sk.channel();

                   // 14、读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                     int len =0;
                     while ((len=sChannel.read(buf))!=-1){
                         buf.flip();
                         System.out.println(new String(buf.array()));
                         buf.clear();
                     }
                    System.out.println("-------------------------");
                }
                // 15、取消选择的 SelectionKey
                it.remove();
            }


        }

    }

    /**
     * 两数交换
     */
    @Test
    public void test3(){
        int a =20;
        int b=399;
        a =a^b;
        b=a^b;
        a=a^b;
        System.out.println(a);
        System.out.println(b);
    }
}
