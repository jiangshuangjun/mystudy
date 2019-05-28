package study.jdk8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 一、Lambda 表达式的基础语法
 *     Java 8 中引入了一种新的操作符 "->" ，该操作符称为箭头操作符或 Lambda 操作符，箭头操作符将 Lambda 表达式拆分成两部分：
 *         左侧：Lambda 表达式的参数列表
 *         右侧：Lambda 表达式中所需执行的功能，即 Lambda 体
 *
 *     语法格式示例
 *         语法格式一：无参数，无返回值
 *             () -> System.out.println("Hello Lambda!");
 *
 *         语法格式二：有一个参数，无返回值
 *             (x) -> System.out.println(x);
 *
 *         语法格式三：只有一个参数，无返回值，Lambda 表达式左侧小括号可省略
 *             x -> System.out.println(x)
 *
 *         语法格式四：有多个（两个或两个以上）参数，有返回值，且 Lambda 体中有多条语句
 *             Comparator<Integer> comparator = (x, y) -> {
 *                 System.out.println("函数式接口");
 *                 return Integer.compare(x, y);
 *             };
 *
 *         语法格式五：若 Lambda 体中只有一条语句，return 和大括号都可以省略不写
 *             Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
 *
 *         语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为 JVM 编译器可以通过上下文推断出数据类型，即“类型推断”
 *             Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x, y);
 *
 *         横批：能省则省
 *         上联：左右遇一括号省
 *         下联：左侧推断类型省
 *
 * 二、Lambda 表达式需要“函数式接口”的支持
 *     函数式接口：接口中只有一个抽象方法的接口，称为函数式接口，可以使用注解 @FunctionalInterface 修饰，可用于检查该接口是否是函数式接口
 *
 * 三、Java 8 内置的四大核心函数式接口
 *     Consumer<T> : 消费型接口
 *         void accept(T t);
 *
 *     Supplier<T> : 供给型接口
 *         T get();
 *
 *     Function<T, R> : 函数型接口
 *         R apply(T t);
 *
 *     Predicate<T> : 断言型接口
 *         boolean test(T t);
 *
 * 四、Java 8 提供的其他函数式接口
 *     BiFunction<T, U, R>
 *         参数类型：T, U
 *         返回类型：R
 *         用途：对类型为 T, U 参数应用操作，返回 R 类型的结果。包含方法为 R apply(T t, U u);
 *
 *     UnaryOperator<T>
 *         参数类型：T
 *         返回类型：T
 *         用途：对类型为 T 的对象进行一元运算，并返回 T 类型的结果。包含方法为：T apply(T t);
 *
 *     BinaryOperation<T> (BiFunction 子接口)
 *         参数类型：T, T
 *         返回类型：T
 *         用途：对类型为 T 的对象进行二元运算，并返回 T 类型的结果。包含方法为 T apply(T t1, T t2);
 *
 *     BiConsumer<T, U>
 *         参数类型：T, U
 *         返回类型：void
 *         用途：对类型为 T, U 参数应用操作。包含方法为 void accept(T t, U u);
 *
 *     ToIntFunction<T>、ToLongFunction<T>、ToDoubleFunction<T>
 *         参数类型：T
 *         返回类型：int、long、double
 *         用途：分别计算返回 int、long、double 值的函数
 *
 *     IntFunction<R>、LongFunction<R>、DoubleFunction<R>
 *         参数类型：int, long, double
 *         返回类型：R
 *         用途：参数分别为 int、long、double 类型的函数
 */
public class LambdaDemo {

    @Test
    public void Test1() {
        Runnable runnable = () -> System.out.println("Hello Lambda!");
        runnable.run();
    }

    @Test
    public void test2() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("Hello World!");
    }

    @Test
    public void test3() {
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("Hello World!");
    }

    @Test
    public void test4() {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };

        System.out.println(comparator.compare(5, 5));
    }

    @Test
    public void test5() {
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(3, 5));
    }

    @Test
    public void test6() {
        Comparator<Integer> comparator = (Integer x, Integer y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(5, 5));
    }

    /**
     * 需求：对一个数进行运算
     */
    @Test
    public void test7() {
        Integer result1 = operation(100, x -> x * x);
        System.out.println("100 * 100 = " + result1);

        System.out.println("--------------------------");

        Integer result2 = operation(100, x -> x + 200);
        System.out.println("100 + 200 = " + result2);
    }

    public Integer operation(Integer num, IntegerOperationFunc operationFunc) {
        return operationFunc.getValue(num);
    }

    // Consumer<T> 消费型接口
    @Test
    public void test8() {
        happy(5000, money -> System.out.println("买手机花了 " + money + " 元"));
    }

    public void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    // Supplier<T> 供给型接口
    @Test
    public void test9() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));

        System.out.println(numList);
    }

    // 需求：产生指定个数的整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> supplier) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            result.add(supplier.get());
        }

        return result;
    }

    // Function<T, R> 函数型接口
    @Test
    public void test10() {
        String result = strHandler2("\t\t\t\t Hello World!    ", str -> str.trim());
        System.out.println(result);

        System.out.println("--------------------");

        String subString = strHandler2("喜欢流浪地球", str -> str.substring(2, 6));
        System.out.println(subString);
    }

    // 需求：用于处理字符串
    public String strHandler2(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    // Predicate<T> 断言型接口
    @Test
    public void test11() {
        List<String> list = Arrays.asList("Hello", "World", "What's your name", "a", "b");

        // 将长度 > 3 的字符串过滤出来
        List<String> strings = filterStr(list, s -> s.length() > 3);

        System.out.println(strings);
    }

    // 需求：将满足条件的字符串，放入集合中
    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> strList = new ArrayList<>();

        for (String s : list) {
            if (predicate.test(s)) {
                strList.add(s);
            }
        }

        return strList;
    }

}
