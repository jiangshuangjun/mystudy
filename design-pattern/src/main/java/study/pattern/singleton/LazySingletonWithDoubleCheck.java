package study.pattern.singleton;

/**
 * @author jiangsj
 * 懒汉式3——双重锁检查单例
 * 优点:
 *     (1) 由于懒汉式延时加载特性，使用该实例时才实例化，节省了内存资源
 *     (2) 线程安全
 * 缺点:
 *     (1) 如果不加volatile关键词防止指令重排，双重锁检查单例可能会出现不完整实例
 *     (2) 反序列化，反射与克隆可破坏单例
 */
public class LazySingletonWithDoubleCheck {

    /** 私有化类构造器 */
    private LazySingletonWithDoubleCheck() {}

    /** 定义静态私有类对象 */
    private static volatile LazySingletonWithDoubleCheck instance;

    /** 提供公共静态的获取该私有类对象的方法 */
    public static LazySingletonWithDoubleCheck getInstance() {
        if (instance == null) {
            synchronized (LazySingletonWithDoubleCheck.class) {
                if (instance == null) {
                    instance = new LazySingletonWithDoubleCheck();
                }
            }
        }

        return instance;
    }

}
