package oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangsj
 * JVM 参数 : -Xms10m -Xmx10m -XX:+PrintGCDetails
 *
 * GC 回收时间过长时会抛出 : java.lang.OutOfMemoryError: GC overhead limit exceeded。
 *
 * 过长的定义是：
 * 超过 98% 的时间用来做 GC 并且回收了不到 2% 的堆内存，连续多次 GC 都只回收了不到 2% 的极端情况。
 *
 * 假如不抛出 GC overhead limit exceeded 错误会发生什么情况呢？
 * 那就是 GC 清理的这么点内存很快会再次填满，迫使 GC 再次执行，这样会形成恶性循环，CPU 使用率一直是 100%，而 GC 却没有任何成果。
 */
public class GCOverheadLimitExceededDemo {

    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();

        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println(" i = " + i);
            e.printStackTrace();
            throw e;
        }
    }

}
