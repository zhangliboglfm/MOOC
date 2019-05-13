package com.myself.JvmMomeory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JvmTest {

    static List<DataObject> list = new ArrayList();
    /*static {
        //模拟持久带存储
        int index = 0;
        while(index < 10000){
            DataObject object = new DataObject();
            object.setAge(index + 30);
            object.setName("李四"+index);
            list.add(object);
            index ++;
        }
        System.out.println("生成持久带数据：" + list.size() + "条");
        System.out.println("[1]:生成持久带数据，请观察jconsole堆内存情况");
    }*/

    //模拟新生代存储
    public static void main(String[] agrs){
        try {
            System.out.print("请输入任意键开始给Eden区塞对象");
            System.in.read(); //模拟挂起，观察jconsole
            System.out.print("开始往Eden灌数据");
            List<DataObject> tempList = new ArrayList();
            int i = 100000;
            while(i > 0){
                DataObject object = new DataObject();
                object.setAge(i + 20);
                object.setName("张三"+i);
                i --;
                tempList.add(object);
            }
            System.out.println("生成新生代数据：" + tempList.size() + "条");
            System.out.println("[2]:生成新生代数据完成，请观察jconsole堆内存情况");
            System.in.read();
            System.out.println("输入任意键执行GC回收：");

            System.gc();
            System.out.println("[3]:GC回收完成，请观察jconsole堆内存情况");
            System.in.read();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class DataObject{
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "DataObject{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
