package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ABCPrintDemo_Condition {

    private volatile int flag = 1;
    private volatile int num = 1;
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

            System.out.println(Thread.currentThread().getName() + ":\t" + "A");
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

            System.out.println(Thread.currentThread().getName() + ":\t" + "B");
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

            System.out.println(Thread.currentThread().getName() + ":\t" + "C");
            flag = 1;
            // 这里这个 num 只是让 main 程序用来控制打印多少轮的控制变量
            num++;
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

        while (resource.num <= 3) {}

        System.exit(0);
    }

}
