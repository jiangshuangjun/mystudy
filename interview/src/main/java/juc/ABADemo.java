package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA 问题的解决
 * AtomicStampedReference
 */
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("======以下是 ABA 问题的产生=====");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            // t2 线程暂停 1 秒，保证上面 t1 线程完成一次 ABA 操作
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            System.out.printf("CAS 成功：%s, atomicReference 的当前值是：%d\n", atomicReference.compareAndSet(100, 2019), atomicReference.get());
        }, "t2").start();

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("\n======以下是 ABA 问题的解决=====");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.printf("%s 第 1 次版本号：%d\n", Thread.currentThread().getName(), stamp);

            // 暂停 1 秒钟 t3 线程
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.printf("%s 第 2 次版本号：%d\n", Thread.currentThread().getName(), atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.printf("%s 第 3 次版本号：%d\n", Thread.currentThread().getName(), atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.printf("%s 第 1 次版本号：%d\n", Thread.currentThread().getName(), stamp);

            // 暂停 3 秒钟 t4 线程，保证上面的 t3 线程完成一次 ABA 操作
            try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}

            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.printf("%s 修改成功：%s, 期望版本号：%d，当前最新实际版本号：%d\n", Thread.currentThread().getName(), result, stamp, atomicStampedReference.getStamp());
            System.out.printf("%s 实际最新值：%d\n", Thread.currentThread().getName(), atomicStampedReference.getReference());
        }, "t4").start();
    }

}
