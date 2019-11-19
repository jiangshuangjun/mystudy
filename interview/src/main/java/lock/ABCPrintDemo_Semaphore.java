package lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ABCPrintDemo_Semaphore {

    private static Semaphore A = new Semaphore(1);
    private static Semaphore B = new Semaphore(0);
    private static Semaphore C = new Semaphore(0);
    private AtomicInteger num = new AtomicInteger(1);

    public void printA() {
        try {
            A.acquire();

            System.out.println(Thread.currentThread().getName() + ":\t" + "A");
            TimeUnit.SECONDS.sleep(1);

            B.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printB() {
        try {
            B.acquire();

            System.out.println(Thread.currentThread().getName() + ":\t" + "B");
            TimeUnit.SECONDS.sleep(1);

            C.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printC() {
        try {
            C.acquire();

            System.out.println(Thread.currentThread().getName() + ":\t" + "C");
            // 这个 num 是让 main 线程用来控制结束的标志
            num.getAndIncrement();
            TimeUnit.SECONDS.sleep(1);

            A.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ABCPrintDemo_Semaphore resource = new ABCPrintDemo_Semaphore();

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
