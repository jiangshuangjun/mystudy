package study.pattern.singleton;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * @author jiangsj
 * {@link SerializableSingleton} Test
 */
@Slf4j
public class SerializableSingletonTest {

    @Test
    public void serializableSingletonTest() throws Exception {
        SerializableSingleton singleton = SerializableSingleton.getInstance();

        // 将 singleton 序列化，持久化到磁盘文件 serializableSingleton.txt
        String fileName = serializeObjectToFile("serializableSingleton.txt", singleton);

        // 从磁盘文件 serializableSingleton.txt 中反序列化出对象 deserializeSingleton
        SerializableSingleton deserializeSingleton = null;
        deserializeSingleton = (SerializableSingleton) deserializeFileToObject(fileName, deserializeSingleton);

        log.debug("序列化时对象：{}", singleton.toString());
        log.debug("反序列化后对象：{}", deserializeSingleton.toString());

        // 反序列化成功后删除磁盘对象文件
        deleteFile(fileName);

        // 验证反序列化出的对象与进行序列化的对象是否是同一个
        Assert.assertEquals(singleton.toString(), deserializeSingleton.toString());
    }

    /**
     * 将对象序列化到对象文件中
     * @param fileName
     * @param obj
     * @return
     */
    public static String serializeObjectToFile(String fileName, Object obj) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(new File(fileName));
            oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileName;
    }

    /**
     * 将对象从对象文件中反序列化
     * @param fileName
     * @param obj
     * @return
     */
    public static Object deserializeFileToObject(String fileName, Object obj) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);

            obj = ois.readObject();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return obj;
    }

    /**
     * 删除单个文件
     * @param fileName
     * @return 是否删除文件成功
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);

        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.debug("删除文件 {} 成功", fileName);
                return true;
            } else {
                log.debug("删除文件 {} 失败", fileName);
                return false;
            }
        } else {
            log.debug("删除文件失败，{} 不存在", fileName);
            return false;
        }
    }

}