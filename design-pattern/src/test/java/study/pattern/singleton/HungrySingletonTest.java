package study.pattern.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * @author jiangsj
 * {@link HungrySingleton} Test
 */
@Slf4j
public class HungrySingletonTest {

    /**
     * 并发获取 HungrySingleton 实例 Test
     */
    @Test
    public void concurrentGetHungrySingletonTest() throws Exception {
        // 模拟并发的线程数
        int count = 500;

        // 发令枪
        final CountDownLatch workThreadLatch = new CountDownLatch(count);
        final CountDownLatch mainThreadLatch = new CountDownLatch(count);

        // 统计并发获取到的单例对象引用地址，用于验证获取到的单例是否是同一个
        final List<String> instanceUrlList = new Vector<String>();

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        // 阻塞，count = 0 就会释放所有的共享锁，模拟多线程并发获取单例
                        workThreadLatch.await();

                        HungrySingleton instance = HungrySingleton.getInstance();

                        log.debug("当前时间: {}, 单例地址: {}", System.currentTimeMillis(), instance.toString());

                        // 将每次获取到的单例引用地址添加到list中，用于验证是否获取到同一单例
                        instanceUrlList.add(instance.toString());

                        // 每循环一次，就启动一个线程；每启动一个线程，count--
                        mainThreadLatch.countDown();
                    } catch (Exception e) {
                        log.error("线程 {} 异常: {}", Thread.currentThread().getName(), e);
                    }
                }
            }.start();

            // 每循环一次，就启动一个线程；每启动一个线程，count--
            workThreadLatch.countDown();
        }

        // 阻塞，count = 0 再执行main线程下面代码
        mainThreadLatch.await();

        long end = System.currentTimeMillis();

        log.debug("并发获取单例总耗时: {}", (end - start));
        Assert.assertEquals("线程数：" + count, "线程数：" + instanceUrlList.size());

        // 验证是否获取到不一致的单例
        for (int i = 0; i < instanceUrlList.size(); i++) {
            if (i == instanceUrlList.size() - 1) {
                Assert.assertEquals(instanceUrlList.get(i - 1), instanceUrlList.get(i));
                break;
            }

            Assert.assertEquals(instanceUrlList.get(i), instanceUrlList.get(i + 1));
        }
    }

    /**
     * 反射破坏 HungrySingleton 单例测试
     */
    @Test
    public void breakSingletonByReflectionTest() throws Exception {
        HungrySingleton singleton = HungrySingleton.getInstance();

        Constructor<HungrySingleton> constructor = HungrySingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        HungrySingleton singletonFromReflection = constructor.newInstance();

        log.debug("正常单例：{}", singleton.toString());
        log.debug("反射单例：{}", singletonFromReflection.toString());

        Assert.assertEquals(singleton.toString(), singletonFromReflection.toString());
    }

}