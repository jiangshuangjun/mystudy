package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrintDemo_watiNotify {

    private volatile int flag = 1;
    private AtomicInteger num = new AtomicInteger(1);

    public void printA() {
        try {
            synchronized (this) {
                while (flag != 1) {
                    this.wait();
                }

                System.out.printf("%s:\tA\n", Thread.currentThread().getName());
                flag = 2;
                TimeUnit.SECONDS.sleep(1);
                this.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printB() {
        try {
            synchronized (this) {
                while (flag != 2) {
                    this.wait();
                }

                System.out.printf("%s:\tB\n", Thread.currentThread().getName());
                flag = 3;
                TimeUnit.SECONDS.sleep(1);
                this.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printC() {
        try {
            synchronized (this) {
                while (flag != 3) {
                    this.wait();
                }

                System.out.printf("%s:\tC\n", Thread.currentThread().getName());
                flag = 1;
                // 这个 num 是让 main 线程用来控制结束的标志
                num.getAndIncrement();
                TimeUnit.SECONDS.sleep(1);
                this.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ABCPrintDemo_watiNotify resource = new ABCPrintDemo_watiNotify();

        new Thread(() -> {
            while (true) {
                resource.printA();
            }
        }, "A").start();

        new Thread(() -> {
            while (true) {
                resource.printB();
            }
        }, "B").start();

        new Thread(() -> {
            while (true) {
                resource.printC();
            }
        }, "C").start();

        while (resource.num.get() <= 3) {}

        System.exit(0);
    }

}
