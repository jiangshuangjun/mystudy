package study.throwable.error.oom;

/**
 * @author jiangsj
 * JVM 参数 : -Xms10m -Xmx10m
 */
public class JavaHeapSpaceDemo {

    public static void main(String[] args) {
        // new 一个 11MB 的对象，直接撑爆最大堆内存
        Byte[] bytes = new Byte[11 * 1024 * 1024];
    }

}
