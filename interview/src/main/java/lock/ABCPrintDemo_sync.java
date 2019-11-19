package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ABCPrintDemo_sync {

    private volatile boolean shouldA = true;
    private volatile boolean shouldB = false;
    private volatile boolean shouldC = false;
    private AtomicInteger num = new AtomicInteger(1);

    public void printA() {
        while (!shouldA) {}

        synchronized (this) {
            System.out.printf("%s:\tA\n", Thread.currentThread().getName());
            shouldA = false;
            shouldB = true;
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public void printB() {
        while (!shouldB) {}

        synchronized (this) {
            System.out.printf("%s:\tB\n", Thread.currentThread().getName());
            shouldB = false;
            shouldC = true;
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public void printC() {
        while (!shouldC) {}

        synchronized (this) {
            System.out.printf("%s:\tC\n", Thread.currentThread().getName());
            shouldC = false;
            shouldA = true;
            num.getAndIncrement();
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public static void main(String[] args) {
        ABCPrintDemo_sync resource = new ABCPrintDemo_sync();

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
