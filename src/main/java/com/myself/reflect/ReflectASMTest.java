package com.myself.reflect;


/**
 *
 * https://www.cnblogs.com/juetoushan/p/7724793.html
 * ReflectASM 使用字节码生成的方式实现了更为高效的反射机制。执行时会生成一个存取类来 set/get 字段，访问方法或创建实例。
 * 一看到 ASM 就能领悟到 ReflectASM 会用字节码生成的方式，而不是依赖于 Java 本身的反射机制来实现的，所以它更快，
 * 并且避免了访问原始类型因自动装箱而产生的问题。
 */
public class ReflectASMTest {
}
