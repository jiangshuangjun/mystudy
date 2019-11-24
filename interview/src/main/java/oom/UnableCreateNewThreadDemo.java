package oom;

/**
 * @author jiangsj
 *
 * 高并发请求服务器时，经常出现如下异常：java.lang.OutOfMemory: unable to create new native thread
 * 准确的讲， native thread 异常与对应的平台有关
 *
 * 导致原因：
 * 1. 你的应用创建了太多线程，一个应用进程创建的多个线程，超过了系统的承载极限
 * 2. 你的服务器不允许你的应用程序创建这么多线程， linux 系统默认允许单个进程可以创建的线程数是 1024 个
 *    你的应用创建线程超过这个数量，就会报 java.lang.OutOfMemory: unable to create new native thread
 *
 * 解决办法：
 * 1. 想办法降低你应用程序创建线程的数量，分析应用是否真的需要创建这么多线程，如果不是，改代码将线程数降低
 * 2. 对于有的应用，确实需要创建很多线程，远超过 linux 系统默认的 1024 个线程的限制，可以通过修改 linux 服务器配置，扩大 linux 默认限制
 *
 * linux 服务器如何查看单个用户进程可以创建的线程数？
 * 1. 使用 ulimit -u 命令查看
 * 2. 直接查看 /etc/security/limits.d/xx-nproc.conf 文件
 */
public class UnableCreateNewThreadDemo {

    public static void main(String[] args) {
        for (int i = 1; ; i++) {
            System.out.printf("****** i = %d\n", i);

            new Thread(() -> {
                try {Thread.sleep(Integer.MAX_VALUE);} catch (InterruptedException e) {e.printStackTrace();}
            }, String.valueOf(i)).start();
        }
    }

}
