package gc;

import java.util.Random;

/**
 * 1
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC  (DefNew + Tenured)
 *
 * 2
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC  (ParNew + Tenured)
 * 备注情况：Java HotSpot(TM) 64-Bit Server VM warning:
 * Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release
 *
 * 3
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC  (PSYoungGen + ParOldGen)
 *
 * 4
 * 4.1
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC  (PSYoungGen + ParOldGen)
 * 4.2 不加就是默认 UseParallelGC
 * -Xms10m -Xmx10x -XX:+PrintGCDetails -XX:+PrintCommandLineFlags  (PSYoungGen + ParOldGen)
 *
 * 5
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC  (par new generation + concurrent mark-sweep)
 *
 * 6
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC  (G1GC 有单独 demo)
 *
 * 7 (理论指导即可，实际中 java8 已经被优化掉了，没有了)
 * -Xms10m -Xmx10m -XX:+PrintGcDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC
 *
 * 下面是故意繁琐配置，主要是为了学习，一般生产不这么配置：
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC -XX:+UseParallelOldGC  (PSYoungGen + ParOldGen)
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC -XX:+UseConcMarkSweepGC  (par new generation + concurrent mark-sweep generation)
 */
public class GCDemo {

    public static void main(String[] args) {
        System.out.println("****************** GCDemo Hello");

        try {
            String str = "GCDemo Hello";
            while (true) {
                str += str + new Random().nextInt(777777) + new Random().nextInt(888888);
                str.intern();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
