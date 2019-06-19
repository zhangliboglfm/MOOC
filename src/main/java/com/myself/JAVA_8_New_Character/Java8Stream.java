package com.myself.JAVA_8_New_Character;

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

/**
 * jdk 1.8 Stream 操作
 *
 * https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 */
public class Java8Stream {

    /**
     * Stream()的产生
     *
     *  1. 从 Collection 和数组
     *      Collection.stream()/Collection.parallelStream()
     *      Arrays.stream(T array) or Stream.of()
     *  2. 从 BufferedReader
     *      java.io.BufferedReader.lines()
     *  3. 静态工厂
     *      java.util.stream.IntStream.range()
     *      java.nio.file.Files.walk()
     *  4. 自己构建
     *      java.util.Spliterator
     *      Random.ints()
     *      BitSet.stream()
     *      Pattern.splitAsStream(java.lang.CharSequence)
     *      JarFile.stream()
     */
    @Test
    public void  createStream(){

    }

    /**
     * 流的操作：
     *   在对于一个 Stream 进行多次转换操作 (Intermediate 操作)，每次都对 Stream 的每个元素进行转换，而且是执行多次，
     *   这样时间复杂度就是 N（转换次数）个 for 循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是 lazy 的，多个转换操作只会在 Terminal 操作的时候融合起来，
     *   一次循环完成。我们可以这样简单的理解，Stream 里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在 Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。
     *
     *
     *      Intermediate：
     *          一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，
     *          然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
     *          （map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered）
     *
     *      Terminal：
     *          一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
     *          Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect
     *          （forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator）
     *
     *      short-circuiting：
     *          对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
     *          对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
     *          （anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit）
     */
    @Test
    public void  streamOperate(){

    }
}
