package com.myself.NIO;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.Map;

/**
 * 一、介绍 New IO ：
 *
 *    1.缓冲区（Buffer）和 通道（Channel）
 *    2.文件通道 FileChannel
 *    3.NIO的非阻塞式网络编程
 *          选择器 Selector
 *          SocketChannel、ServerSocketChannel、DatagramChannel
 *    4.管道 Pipe
 *    5. Java NIO2 (Path、Paths 与 Files)
 *
 *  二、IO 与 NIO 的区别？
 *   面向流（stream Oriented）  面向缓冲区（Buffer Oriented）
 *   阻塞IO （Blocking IO）     非阻塞IO （Non Blocking IO）
 *   无                         选择器（Selectors）
 *
 * 三、通道和缓冲区
 *    Java NIO 系统的核心在于： 通道（Channel -- 双向的 ） 和 缓冲区（Buffer）。
 *         通道表示打开到IO设（文件、套接字）备的连接，若需要使用NIO系统，
 *         需要获取用于连接IO设备的通道以及用去容纳数据缓冲区，然后操作缓冲区，对数据进行处理
 *      Channel 负责传输，Buffer 负责存储
 *
 */
public class NIOSummary {


}
