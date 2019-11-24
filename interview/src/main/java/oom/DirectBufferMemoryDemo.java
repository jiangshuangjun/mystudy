package oom;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangsj
 * JVM 参数 : -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *
 * 故障现象：Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
 *
 * 导致原因：
 * 写 NIO 程序经常使用 ByteBuffer 来读取或写入数据，这是一种基于通道(Channel)和缓冲区(Buffer)的 I/O 方式，
 * 它可以使用 Native 函数库直接分配堆外内存，然后通过一个存储在 Java 堆里的 DirectByteBuffer 对象作为这块内存的引用进行操作。
 * 这样能在一些场景中显著提高性能，因为避免了在 Java 堆和 Native 堆中来回复制数据。
 *
 * ByteBuffer.allocate(int capability) 这种方式是分配 JVM 内存，属于 GC 管辖范围，由于需要拷贝所以速度相对较慢；
 *
 * ByteBuffer.allocateDirect(int capability) 这种方式是分配 OS 本地内存，不属于 GC 管辖范围，由于不需要内存拷贝所以速度相对较快。
 *
 * 但如果不断分配本地内存，堆内存很少使用，那么 JVM 就不需要执行 GC ，DirectByteBuffer 对象们就不会被回收，
 * 这时候堆内存充足，但本地内存可能已经使用光了，再次尝试分配本地内存就会出现 OutOfMemoryError，导致程序崩溃。
 */
public class DirectBufferMemoryDemo {

    public static void main(String[] args) {
        System.out.printf("配置的 maxDirectMemory:\t%d%s\n", (sun.misc.VM.maxDirectMemory() / 1024 /1024), "MB");

        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

        // -XX:MaxDirectMemory=5m，我们配置为 5MB，但实际使用 6MB
        ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }

}
