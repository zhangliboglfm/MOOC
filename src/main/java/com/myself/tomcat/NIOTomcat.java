package com.myself.tomcat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用 NIO 实现一个简单的Tomcat
 *
 *  https://www.cnblogs.com/caoxb/p/9326733.html
 */
public class NIOTomcat {


    public void start()  throws IOException{

        // 1.新建NIO通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8888));
        ssc.configureBlocking(false); // 设置为非阻塞状态
        ServerSocket serverSocket = ssc.socket();
        System.out.println("启动web服务");
//        serverSocket.bind(new InetSocketAddress(8888));

        while (true){
            SocketChannel channel = ssc.accept();
            if(channel!=null){
                Thread thread = new Thread(new HttpServerThread(channel));
                thread.start();
            }
        }
    }

    // 内部类
    private class HttpServerThread implements Runnable{

        SocketChannel socketChannel;

        HttpServerThread(SocketChannel socketChannel){
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            if(socketChannel!=null){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    InetSocketAddress remoteSddress =(InetSocketAddress) socketChannel.getRemoteAddress();
                    System.out.println(remoteSddress.getAddress());
                    System.out.println(remoteSddress.getPort());
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()){
                        char c  = (char) byteBuffer.get();
                        System.out.print(c);
                    }
                    // 此处打印执行的线程名称，永远为 main 线程
                    System.out.println(Thread.currentThread().getName() + "开始向web返回消息。。。");
                    ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
                    // 给客户端一个响应，即向输出流写入信息
                    String reply = "HTTP/1.1\n"; // 必须添加的响应头
                    reply += "Content-type:text/html;charset=UTF-8\n\n"; // 必须添加的响应头,此处设置的编码需要和返回的编码一样，如果不设置，默认为GBK
                    reply += "服务器返回的消息";
                    // 对应 Content-type 处设置的编码，
                    // 如果Content-type处不设置，浏览器解析模式为 GBK, 而我们系统模式的为UTF-8，会乱码
                    byteBuffer2.put(reply.getBytes("utf-8"));
                    byteBuffer2.flip();
                    socketChannel.write(byteBuffer2);
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NIOTomcat().start();
    }
}
