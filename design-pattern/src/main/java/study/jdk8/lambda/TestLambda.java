package study.jdk8.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 1. 调用 Collections.sort() 方法，通过定制排序比较两个 Employee(先按年龄比，年龄相同按姓名比)，使用 Lambda 作为参数传递
 * <p>
 * 2. 1）声明函数式接口，接口中生命抽象方法，public String getValue(String str);
 * 2）声明类 TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值
 * 3）再将一个字符串的第 2 个和第 6 个索引位置进行截取子串
 * <p>
 * 3. 1）声明一个带两个泛型的函数式接口，泛型类型为 <T, R> ，T 为参数，R 为返回值
 * 2）接口中声明对应抽象方法
 * 3）在 TestLambda 类中声明方法，使用接口作为参数，计算两个 long 型参数的和
 * 4）再计算两个 long 型参数的乘机
 */
public class TestLambda {

    private List<Employee> emps = Arrays.asList(
            new Employee(100, "张三", 18, 9999.99),
            new Employee(101, "李四", 40, 3000.98),
            new Employee(102, "王五", 36, 13000.76),
            new Employee(103, "赵六", 36, 8500.43),
            new Employee(104, "田七", 16, 6800.76),
            new Employee(105, "王八", 31, 2500.87)
    );

    @Test
    public void test1() {
        Collections.sort(emps, (e1, e2) -> {
            if (e1.getAge() == e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            } else {
                return Integer.compare(e1.getAge(), e2.getAge());
            }
        });

        for (Employee emp : emps) {
            System.out.println(emp);
        }
    }

    @Test
    public void test2() {
        // 去除字符串首尾空格
        String result = strHandler("\t\t\t\t\t Hello World!  ", str -> str.trim());
        System.out.println(result);

        System.out.println("-------------------");

        String result1 = strHandler("hello world!", str -> str.toUpperCase());
        System.out.println(result1);

        System.out.println("-------------------");

        String result2 = strHandler("喜欢流浪地球", str -> str.substring(2, 6));
        System.out.println(result2);
    }

    @Test
    public void test3() {
        // 计算 100 + 200
        op(100L, 200L, (x, y) -> x + y);

        // 计算 123 * 546
        op(123L, 546L, (x, y) -> x * y);
    }

    // 用于处理字符串
    public String strHandler(String str, StringFunc strFunc) {
        return strFunc.getValue(str);
    }

    // 需求：对两个 Long 型数据进行处理
    public void op(Long l1, Long l2, LongFunc<Long, Long> longFunc) {
        System.out.println(longFunc.getValue(l1, l2));
    }

}
