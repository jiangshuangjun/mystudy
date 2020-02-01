import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class ThreadLoalDemo {

    private ThreadLocal threadLocal;

    public void setThreadLoca() {
        if (threadLocal == null) {
            threadLocal = new ThreadLocal();
        }

//        threadLocal.set(new Byte[]{1024 * 1024 * 6});
    }

    public static void main(String[] args) {
//        new Thread(() -> {
//
//        }, "A").start();
        Integer object = new Integer(1);

        WeakReference weakReference = new WeakReference(object);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());
        object = null;
        System.out.println(weakReference.get());
        System.gc();
        System.out.println("null and gc： " + weakReference.get());
    }

}
