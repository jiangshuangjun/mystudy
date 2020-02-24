package juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 是什么？ ==> compareAndSet
 * 比较并交换
 */
public class CASDemo {
    public static void main(String[] args){
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.printf("比较并交换是否成功：%s, atomicInteger current value is: %d\n", atomicInteger.compareAndSet(5, 2019), atomicInteger.get());
        System.out.printf("比较并交换是否成功：%s, atomicInteger current value is: %d\n", atomicInteger.compareAndSet(5, 1024), atomicInteger.get());
    }
}