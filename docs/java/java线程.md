##  线程的生命周期，线程有哪些状态

创建、就绪、运行、阻塞和死亡
**阻塞：**
（1）等待阻塞：运行的线程执行wait()方法，该线程会释放占用的所有资源，JVM会把该线程放入“等待池”中。进入这个状态后，是不能自动唤醒的，必须依靠其他线程调用notify或notifyAll方法才能被唤醒。

（2）同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入“锁池”中。

（3）其他阻塞：运行的线程执行sleep或join方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep状态超时、Join等待线程终止或者超时、或者I/O处理完毕时，线程重新进入就绪状态。

## 线程通信


## 线程池的优点

（1）提高线程的利用率

（2）提高程序的响应速度

（3）便于统一管理线程对象

（4）可以控制最大并发数

```java
import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService e = new ThreadPoolExecutor(3, 5, 1l, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        for (int i=0; i<50; i++) {
            e.execute(()->{
                System.out.println(Thread.currentThread().getName() + "办理业务");
            });
        }
    }
}

```

