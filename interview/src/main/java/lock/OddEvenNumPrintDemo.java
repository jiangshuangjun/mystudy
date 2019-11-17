package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 奇偶数交替打印
 */
public class OddEvenNumPrintDemo {

    private volatile int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void printOddNum() {
        lock.lock();
        try {
            while ((num % 2) == 0) {
                condition.await();
            }

            System.out.println(Thread.currentThread().getName() + "\t" + num);
            num++;
            TimeUnit.SECONDS.sleep(1);

            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printEvenNum() {
        lock.lock();
        try {
            while ((num % 2) != 0) {
                condition.await();
            }

            System.out.println(Thread.currentThread().getName() + "\t" + num);
            num++;
            TimeUnit.SECONDS.sleep(1);

            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        OddEvenNumPrintDemo resource = new OddEvenNumPrintDemo();

        new Thread(() -> {
            while (true) {
                resource.printOddNum();
            }
        }, "A").start();

        new Thread(() -> {
            while (true) {
                resource.printEvenNum();
            }
        }, "B").start();

        while (resource.num <= 10) {}

        System.exit(0);
    }

}
