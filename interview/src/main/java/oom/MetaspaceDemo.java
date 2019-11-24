package oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author jiansj
 *
 * JVM 参数：
 * -XX:MetaspaceSize=15m -XX:MaxMetaspaceSize=15m
 *
 * Java8 之后的版本使用 Metaspace 来替代永久代
 *
 * Metaspace 是方法区在 HotSpot 中的实现
 * 它与持久带最大的区别在于：Metaspace并不在虚拟机内存中而是使用本地内存
 * 也即在java8中，class metaspace（the virtual machines internal presentation of java class）,被存储在叫做 Metaspace 的 native memory 中
 *
 * 永久代（Metaspace）存放以下信息：
 * 虚拟机加载的类信息
 * 运行时常量池（class文件元信息描述，编译后的代码数据，引用类型数据，类文件常量池）
 * 静态变量
 * 即时编译后的代码
 *
 * 模拟 Metaspace 空间溢出，我们不断生成类往元空间灌，类占据的空间总是会超过 MetaSpace 指定的空间大小
 */
public class MetaspaceDemo {

    static class OOMTest {}

    public static void main(String[] args) {
        // 模拟多少次后发生异常
        int i = 0;

        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println("********多少次后发生了异常：" + i);
            e.printStackTrace();
        }
    }
}
