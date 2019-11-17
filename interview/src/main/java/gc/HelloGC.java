package gc;

/**
 * 面试题：你说你做过 JVM 调优和参数配置，请问如何盘点查看 JVM 系统默认值？
 *
 * JVM 的参数类型：
 *  标配参数：
 * 	    1. -version
 * 	    2. -help
 * 	    3. java -showversion
 *  X 参数（了解）
 * 	    1. -Xint 解释执行
 * 	    2. -Xcomp 第一次使用就编译成本地代码
 * 	    3. -Xmixed 混合模式
 *  XX 参数（核心）：
 * 	    1. Boolean 类型
 * 		    公式：
 * 			    -XX:+或者-某个属性值
 * 			    +表示开启
 * 			    -表示关闭
 * 		    Case:
 * 			    是否打印GC 收集细节：
 * 				    -XX:+PrintGCDetails
 * 				    -XX:-PrintGCDetails
 * 			    是否使用串行垃圾回收器：
 * 				    -XX:+UseSerialGC
 * 				    -XX:-UseSerialGC
 * 	    2. KV 设值类型
 * 	        公式：-XX:属性key=属性值value
 * 	        Case：
 * 	            -XX:MetaspaceSize=128m
 * 	            -XX:MaxTenuringThreshold=15
 * 	    3. jinfo 举例，如何查看当前运行程序的配置
 * 	        公式：jinfo -flag 配置项 Java进程编号
 * 	        case1: 查看是否打印GC 收集细节（JVM 参数：-XX:+PrintGCDetails，可通过不配置该参数查看默认状态）
 * 	            1. 运行程序
 * 	            2. 命令行窗口按次序输入
 * 	                jps -l  // 查找当前运行的 java 进程编号 PID
 * 	                jinfo -flag PrintGCDetails PID
 * 	        case2：查看是否使用串行垃圾回收器（JVM 参数：-XX:+UseSerialGC，可通过不配置该参数查看默认状态）
 * 	            1. 运行程序
 * 	            2. 命令行窗口按次序输入
 * 	                jps -l  // 查找当前运行的 java 进程编号 PID
 * 	                jinfo -flag UseSerialGC PID
 * 	        case3：查看 MetaspaceSize 初始值（JVM 参数：-XX:MetaspaceSize=128m，可通过不配置该参数查看默认状态）
 * 	            1. 运行程序
 * 	            2. 命令行窗口按次序输入
 * 	                jps -l  // 查找当前运行的 java 进程编号 PID
 * 	                jinfo -flag MetaspaceSize PID  // 默认值：-XX:MetaspaceSize=21807104
 * 	        case4：查看 MaxTenuringThreshold 初始值（JVM 参数：-XX:MaxTenuringThreshold=10，可通过不配置该参数查看默认状态）
 * 	            1. 运行程序
 * 	            2. 命令行窗口按次序输入
 * 	                jps -l  // 查找当前运行的 java 进程编号 PID
 * 	                jinfo -flag MaxTenuringThreshold PID  // 默认值：-XX:MaxTenuringThreshold=15
 * 	        case5：查看初始化堆内存大小，InitialHeapSize（JVM 参数：-XX:+InitialHeapSize，可通过不配置该参数查看默认状态）
 * 	            1. 运行程序
 * 	            2. 命令行窗口按次序输入
 * 	                jps -l  // 查找当前运行的 java 进程编号 PID
 * 	                jinfo -flag InitialHeapSize PID  // 默认值：大约物理内存的 1/64
 * 	        case6：查看所有默认初始参数
 * 	            1. 运行程序
 * 	            2. 命令行窗口按次序输入
 * 	                jps -l  // 查找当前运行的 java 进程编号 PID
 * 	                jinfo -flags PID
 * 	    4. 题外话（坑题）
 * 	        两个经典参数：-Xms 和 -Xmx，属于哪种类型的 JVM 参数？
 * 	        -Xms 等价于 -XX:InitialHeapSize
 * 	        -Xms 等价于 -XX:MaxHeapSize
 *
 * 如何查看 JVM 默认值：（无需 JVM 进程编号，只要安装了 java 环境就可以进行查看）
 * -XX:+PrintFlagsInitial
 *      主要查看初始默认值
 *      公式：
 *          java -XX:+PrintFlagsInitial -version
 *          java -XX:+PrintFlagsInitial
 *      case：
 *          命令行窗口直接运行上述命令即可
 * -XX:+printFlagsFinal
 *      主要查看修改更新
 *      公式：
 *          java -XX:+PrintFlagsFinal -version
 *          java -XX:+PrintFlagsFinal
 *      case：
 *          命令行窗口直接运行上述命令即可，示例：
 *              java -XX:+PrintFlagsFinal -Xss128k T  // 这里的 T 指的是运行中的 java 类的名字
 * -XX:+PrintCommandLineFlags
 *      打印命令行参数，主要查看打印的最后一个参数，显示的是 GC 收集器相关信息
 *
 */
public class HelloGC {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello GC");

        // 让当前主线程阻塞，方便查看 JVM 各种运行中的参数细节
        Thread.sleep(Integer.MAX_VALUE);
    }

}
