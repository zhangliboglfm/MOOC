package com.myself.JAVA_8_New_Character;

import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * jdk 1.8 Stream 操作
 *
 * https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 *
 * 简单快速的处理Stream()
 *      https://www.jianshu.com/p/0bb4daf6c800
 *
 *
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
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);


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
    public void  operate(){

    }

    /**
     *
     * forEach 是 terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了，你无法对一个 Stream 进行两次 terminal 运算
     *
     * 具有相似功能的 intermediate 操作 peek 可以达到上述目的。
     */
    @Test
    public void peekOrForEach(){
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                /*.forEach(e -> System.out.println("Filtered value: " + e))*/
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

    }


    /**
     * reduce 操作：
     *
     */
    @Test
    private void reduceOperate(){
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
    }

    /**
     * limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素（它是由一个叫 subStream 的方法改名而来）
     *
     *  ① 用在 limit(n) 前面时，先去除前 m 个元素再返回剩余元素的前 n 个元素
     *  ② limit(n) 用在 skip(m) 前面时，先返回前 n 个元素再在剩余的 n 个元素中去除 m 个元素
     *
     *
     * limit 和 skip 对 sorted 后的运行次数无影响
     *
     * 最后有一点需要注意的是，对一个 parallel 的 Steam 管道来说，如果其元素是有序的，那么 limit 操作的成本会比较大，
     * 因为它的返回对象必须是前 n 个也有一样次序的元素。取而代之的策略是取消元素间的次序，或者不要用 parallel Stream。
     */
    @Test
    public void skipAndLimit(){
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，为 O(n)，
     * 而 sorted 的成本是 O(n log n)。同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
     *
     * distinct 来找出不重复的单词
     *
     *  allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     *  anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     *  noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     *
     */
    @Test
    public void handle()throws Exception{
        //找出最长一行的长度
        BufferedReader br = new BufferedReader(new FileReader("c:\\SUService.log"));
        int longest = br.lines().
                mapToInt(String::length).
                max().
                getAsInt();
        System.out.println(longest);

        //使用 distinct 来找出不重复的单词
        List<String> words = br.lines().
                flatMap(line -> Stream.of(line.split(" "))).
                filter(word -> word.length() > 0).
                map(String::toLowerCase).
                distinct().
                sorted().
                collect(Collectors.toList());
        br.close();
        System.out.println(words);

    }


    public void groupOrParting(){

        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.groupingBy(Person::getAge));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }


        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.partitioningBy(p -> p.getAge() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }

}

class Person{

    int age;
    String name;
    int random;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public Person(int age, String name, int random) {
        this.age = age;
        this.name = name;
        this.random = random;
    }
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }
}

 class PersonSupplier implements Supplier<Person> {
    private int index = 0;
    private Random random = new Random();
    @Override
    public Person get() {
        return new Person(index++, "StormTestUser" + index, random.nextInt(100));
    }
}