package com.myself.IO;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *   InputStream: 字节输入抽象类（各个字节输入类的祖先基类）
 *
 *
 *   FileUtils: https://www.cnblogs.com/zhaoyanjun/p/6396419.html
 *   IOUtils: https://www.cnblogs.com/zhaoyanjun/p/6401314.html
 */
public class TestInputStream {

    @Test
    public void test1(){
        try {
            InputStream inputStream = new FileInputStream(new File(""));
            InputStream inputStream1 = new ByteArrayInputStream("dfadsfasd".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try{
//            byte[] bytes = new byte[4];
//            InputStream is = IOUtils.toInputStream("hello world");
//            IOUtils.read(is, bytes);
//            System.out.println(new String(bytes));

            byte [] bytes = new byte[10];
            InputStream is = IOUtils.toInputStream("hello world");
            IOUtils.read(is, bytes, 2, 4);
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * readFully：这个方法会读取指定长度的流，如果读取的长度不够，就会抛出异常
     */
    @Test
    public void readFullyTest(){
        byte[] bytes = new byte[4];
        InputStream is  = IOUtils.toInputStream("hello world");
        try {
            IOUtils.readFully(is,bytes);
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        File file =  new File("E:\\applog\\api-user\\info\\log-info-2020-07-06-0.log");

    }


}
