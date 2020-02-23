package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int num = 0;

    public void addTo60() {
        this.num = 60;
    }

    public void addPlusPlus() {
        num++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addMyAtomic() {
        atomicInteger.getAndIncrement();
    }
}

/**
 * 1 验证 volatile 的可见性
 *   1.1 假如 int num = 0，num 变量之前根本没有添加 volatile 关键字修饰,没有可见性
 *   1.2 添加了volatile，可以解决可见性问题
 *
 * 2 验证volatile不保证原子性
 *   2.1 原子性指的是什么意思？
 *       原子性是不可分割，完整性，也即某个线程正在做某个具体业务时，中间不可以被加塞或者分割，需要整体完成，要么同时成功，要么同时失败
 *   2.2 volatile 不保证原子性
 *   2.3 如何解决原子性
 *       - 加 synchronized
 *       - 使用 guc 包下的 AtomicInteger
 */
public class VolatileDemo {

    public static void main(String[] args) {
        // 测试 volatile 的可见性
        testVisibilityOfVolatile();
        // 测试 volatile 的原子性
        testAtomicityOfVolatile();
    }

    /**
     * 测试 volatile 的原子性：volatile 不保证原子性
     */
    private static void testAtomicityOfVolatile() {
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.printf("%s finally num value is: %d\n", Thread.currentThread().getName(), myData.num);
        System.out.printf("%s finally num value is: %d\n", Thread.currentThread().getName(), myData.atomicInteger.intValue());
    }

    /**
     * 测试 volatile 的可见性
     * volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
     */
    private static void testVisibilityOfVolatile() {
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.printf("%s come in...\n", Thread.currentThread().getName());
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
            myData.addTo60();
            System.out.printf("%s updated num value: %d\n", Thread.currentThread().getName(), myData.num);
        }, "A").start();

        while (myData.num == 0) {}

        System.out.printf("%s mission is over, %s get num value: %d\n", Thread.currentThread().getName(), Thread.currentThread().getName(), myData.num);
    }

}
