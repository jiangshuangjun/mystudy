package study.jdk8.streamapi;

import org.junit.Test;
import study.jdk8.lambda.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 了解 Stream :
 *     Java8中有两大最为重要的改变。
 *         第一个是lambda表达式；
 *         另外一个则是Stream APl（java.util.stream.*）。
 *     Stream是Java8中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。
 *     使用StreanAP1对集合数据进行操作，就类似于使用SQL执行的数据库查询。
 *     也可以使用StreamAPI来并行执行操作。
 *     简而言之，Stream API提供了一种高效且易于使用的处理数据的方式。
 *
 * 什么是 Stream ? 流（Stream）到底是什么呢？
 *     是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。"集合讲的是数据，流讲的是计算"。
 *     注意：
 *         1) Stream 自己不会存储元素。
 *         2) Stream 不会改变源对象。相反，他们会返回一个持有结果的新 Stream。
 *         3) Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。
 *
 * Stream 操作的三个步骤：
 *     创建 Stream
 *         一个数据源（如：集合、数组），获取一个流
 *     中间操作
 *         一个中间操作链，对数据源的数据进行处理
 *     终止操作（终端操作）
 *         一个终止操作，执行中间操作链，并产生结果
 */
public class StreamAPIDemo {

    @Test
    public void test1() {
        // 1. 可以通过 Collections 系列集合提供的 stream() 或 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        // 2. 可以通过 Arrays 中的静态方法 stream() 获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(emps);

        // 3. 可以通过 Stream 类中的静态方法 of()
        Stream<String> stream3 = Stream.of("aa", "bb", "cc", "dd");
//        stream3.forEach(System.out :: println);

        // 4. 创建无限流，通过 java.util.stream.Stream.iterate 方法
        Stream<Integer> stream4 = Stream.iterate(0, x -> x + 2);
//        stream4.limit(10).forEach(System.out :: println);

        // 5. 创建无限流，通过 java.util.stream.Stream.generate 方法
        Stream<Double> stream5 = Stream.generate(() -> Math.random());
        stream5.limit(10).forEach(System.out :: println);
    }
}
