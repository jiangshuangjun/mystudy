package gc;

import java.util.Random;

/**
 * 示例 JVM 参数：
 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 *
 * G1 常用设置参数：
 * -XX:+UseG1GC
 * -XX:G1HeapRegionSize=n  设置 G1 区域的大小。值是 2 的幂，范围是 1MB 到 32MB。目标是根据最小的 Java 堆大小划分出约 2048 个区域
 * -XX:MaxGCPauseMills=n  最大 GC 停顿时间，这是个软目标，JVM 将尽可能（但不保证）停顿小于这个时间
 * -XX:InitialHeapOccupancyPercent=n  堆占用了多少的时候就触发 GC，默认为 45
 * -XX:ConcGCThreads=n  并发 GC 使用的线程数
 * -XX:G1ReservePercent=n  设置作为空闲空间的预留内存百分比，以降低目标空间溢出的风险，默认是 10%
 */
public class G1Demo {

    public static void main(String[] args) {
        String str = "Hello G1 GC";
        while (true) {
            str += str + new Random(77777777).nextInt() + new Random(8888888).nextInt();
            str.intern();
        }
    }

}
