package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrintDemo_Condition {

    private volatile int flag = 1;
    private AtomicInteger num = new AtomicInteger(1);
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            while (flag != 1) {
                c1.await();
            }

            System.out.printf("%s:\tA\n", Thread.currentThread().getName());
            flag = 2;
            TimeUnit.SECONDS.sleep(1);

            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            while (flag != 2) {
                c2.await();
            }

            System.out.printf("%s:\tB\n", Thread.currentThread().getName());
            flag = 3;
            TimeUnit.SECONDS.sleep(1);

            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            while (flag != 3) {
                c3.await();
            }

            System.out.printf("%s:\tC\n", Thread.currentThread().getName());
            flag = 1;
            // 这个 num 是让 main 线程用来控制结束的标志
            num.getAndIncrement();
            TimeUnit.SECONDS.sleep(1);

            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ABCPrintDemo_Condition resource = new ABCPrintDemo_Condition();

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
