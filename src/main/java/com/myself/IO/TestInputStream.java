package com.myself.IO;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.messaging.handler.annotation.SendTo;

import java.io.*;
import java.util.*;
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
    @Test
    public void test4() throws IOException {
      FileOutputStream fos = new FileOutputStream("");
      new BufferedOutputStream(fos).flush();

    }

    //从键盘输入字符串，转化为大写，直到输入e或exit退出
    public static void main(String[] args) {
        BufferedReader br=null;
        try {
            InputStream is=System.in;
            InputStreamReader isr=new InputStreamReader(is);
            br = new BufferedReader(isr);
            String str;
            while(true){
                System.out.println("请输入字符串");
                str=br.readLine();
                if(str.equalsIgnoreCase("e")||str.equalsIgnoreCase("exit")){
                    break;
                }
                String str1=str.toUpperCase();
                System.out.println(str1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //打印流：字节流 PrintStream  字符流PrintWriter
    @Test
    public void printStreamWriter(){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(new File("print.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //创建打印输出流，设置为自动刷新模式（写入换行符或字节'\n'时都会刷新输出缓冲区）
        PrintStream ps=new PrintStream(fos,true);
        if(ps!=null){
            System.setOut(ps);//改变输出方式，默认是控制台
        }
        for(int i=0;i<=255;i++){//制造内容，输出ASCII字符
            System.out.print((char)i);
            if(i%50==0){//每50个数据一行
                System.out.println();//换行
            }
        }
        ps.close();
    }

    //进行文件的读、写
    @Test
    public void test6(){
        RandomAccessFile raf1=null;
        RandomAccessFile raf2=null;
        try {
            raf1=new RandomAccessFile(new File("hello.txt"), "r");
            raf2=new RandomAccessFile(new File("hello1.txt"), "rw");
            byte[] b=new byte[20];
            int len;
            while((len=raf1.read(b))!=-1){
                raf2.write(b, 0, len);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(raf2!=null){
                try {
                    raf2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(raf1!=null){
                try {
                    raf1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //覆盖相应位置后面的字符
    @Test
    public void test7(){
        RandomAccessFile raf=null;
        try {
            raf=new RandomAccessFile(new File("hello1.txt"), "rw");
            raf.seek(3);//找到3的位置
            raf.write("xy".getBytes());//在覆盖相应字节数

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(raf!=null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    //实现插入的效果，在d字符后面插入"xy"
    @Test
    public void test8(){
        RandomAccessFile raf=null;
        try {
            raf=new RandomAccessFile(new File("hello1.txt"), "rw");
            raf.seek(4);//找到d的位置
            String str=raf.readLine();
//          long l=raf.getFilePointer();
//          System.out.println(l);
            raf.seek(4);
            raf.write("xy".getBytes());//在覆盖相应字节数
            raf.write(str.getBytes());
            raf.write(System.getProperty("line.separator").getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(raf!=null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test9(){
//        System.out.println(System.getProperty("line.separator"));
//        System.out.println(System.getProperty("path.separator"));
//        System.out.println(System.getProperty("file.separator"));
//        System.out.println(File.separator);
        int cap =9;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;;
        System.out.println(n);
    }

    @Test
    public void test10(){
       LinkedHashMap map = new LinkedHashMap<String, String>(10, 0.75f, true);
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        map.get("2");//2移动到了内部的链表末尾
        map.get("4");//4调整至末尾
        map.put("3", "e");//3调整至末尾
        map.put(null, null);//插入两个新的节点 null
        map.put("5", null);//5
        Iterator  iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }


}

class A {

    private int num;
    private String name;


    public A(int num, String name) {
        this.num = num;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        A a = (A) o;
        return num == a.num &&
                Objects.equals(name, a.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(num);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
