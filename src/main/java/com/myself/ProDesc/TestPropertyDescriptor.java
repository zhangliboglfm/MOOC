package com.myself.ProDesc;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 测试 PropertyDescriptor
 */
public class TestPropertyDescriptor implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;
    public int age;

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

    public TestPropertyDescriptor() {
    }

    public TestPropertyDescriptor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestPropertyDescriptor{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    public static void main(String[] args) {
        TestPropertyDescriptor testPropertyDescriptor = new TestPropertyDescriptor("测试",10);
        Class<TestPropertyDescriptor> clazz = TestPropertyDescriptor.class;
        Field [] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            System.out.println(field.getName());
            try {
                PropertyDescriptor propertyDescriptor  = new PropertyDescriptor(field.getName(),clazz);
                Method method = propertyDescriptor.getReadMethod();
                Object value = method.invoke(testPropertyDescriptor);
                System.out.println(value);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }



        }

    }
}
