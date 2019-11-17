package gc;

/*
 * 面试题：
 * JVM 垃圾回收的时候如何确定垃圾？是否知道什么是 GC Roots？
 *
 * 在java中可作为GC Roots的对象有：
 *
 * 1.虚拟机栈（栈帧中的局部变量区，也叫做局部变量表）中引用的对象。
 * 2.方法区中的类静态属性引用的对象。
 * 3.方法区中常量引用的对象
 * 4.本地方法栈中JNI（Native方法）引用的对象。
 * */
public class GCRootDemo {
    private byte[] byteArray = new byte[100 * 1024 * 1024];

    // 这里的 t2 就是方法区中静态属性引用的变量，对应于类注释中的 2
//    private static GCRootDemo2 t2;
    // 这里的 t3 就是方法区中常亮引用的对象，对应于类注释中的 t3
//    private static final GCRootDemo3 t3 = new GCRootDemo3();

    public static void m1() throws Exception {
        // t1 就是虚拟机栈中引用的对象，对应于类注释中的 1
        GCRootDemo t1 = new GCRootDemo();
        // jvm 运行参数：-XX:+PrintGCDetails
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args) throws Exception {
        m1();
    }

}
