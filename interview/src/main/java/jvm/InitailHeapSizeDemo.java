package jvm;

/**
 * -Xms 堆内存初始大小，默认内存大小：系统64/1
 * -Xmx 堆内存最大值，默认内存大小：系统4/1
 * -Xmn 堆内存年轻代大小
 */
public class InitailHeapSizeDemo {

    public static void main(String[] args) {
        long initialHeapSize = Runtime.getRuntime().totalMemory();
        long maxHeapSize = Runtime.getRuntime().maxMemory();
        System.out.println("-Xms = " + initialHeapSize / 1024 + "Kb or " + initialHeapSize / 1024 / 1024 + "Mb");
        System.out.println("-Xmx = " + maxHeapSize / 1024 + "Kb or " + maxHeapSize / 1024 / 1024 + "Mb");
    }

}
