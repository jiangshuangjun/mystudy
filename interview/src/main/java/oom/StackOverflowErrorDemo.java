package oom;

/**
 * @author jiangsj
 * JVM 参数 : -Xss128k
 */
public class StackOverflowErrorDemo {

    private static int count = 0;

    public static void main(String[] args) {
        try {
            stackOverflowError();
        } catch (Throwable e) {
            System.out.println("递归调用次数 : " + count);
            e.printStackTrace();
            throw e;
        }
    }

    private static void stackOverflowError() {
        count++;
        stackOverflowError();
    }

}
