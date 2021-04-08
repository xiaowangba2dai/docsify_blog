## 为什么需要线程池

线程的创建和销毁很消耗性能，尤其是大规模并发场景下。

线程池起到了`线程复用`的作用，减少开销。

## 线程池的创建方式

**（1）原始创建方式**

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

**（2）封装好的几种创建方式**

所有准备好的线程池都是来自`ThreadPoolExecutor`

```java
Executors.newCachedThreadPool(); // 池子大小非常大，并发运行的线程非常多。
Executors.newFixedThreadPool(10); // 超过10个排队
Executors.newSingleThreadExecutor(); // 只有一个
```

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {
    int i;

    public Task(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "----" + i);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

public class Test{

    public static void main(String[] args) {
        ExecutorService e1 = Executors.newCachedThreadPool(); // 池子大小非常大，并发运行的线程非常多。
        ExecutorService e2 = Executors.newFixedThreadPool(10); // 超过10个排队
        ExecutorService e3 = Executors.newSingleThreadExecutor(); // 只有一个

//        for (int i=0; i<20; i++) {
//            e1.execute(new Task(i));
//        }
        for (int i=0; i<100; i++) {
            e2.execute(new Task(i));
        }
//        for (int i=0; i<20; i++) {
//            e3.execute(new Task(i));
//        }
    }
}
```

## 线程池ThreadPoolExecutor的参数

```java
int corePoolSize, // 1. 核心池大小
int maximumPoolSize, // 2. 最大池子大小
long keepAliveTime, // 3. 存活时间
TimeUnit unit, // 时间单位
BlockingQueue<Runnable> workQueue, // 4. 阻塞队列
ThreadFactory threadFactory, // 线程工厂
RejectedExecutionHandler handler) // 拒绝机制，默认抛出异常
```

```java
new ThreadPoolExecutor(3, 5, 1l, TimeUnit.SECONDS, new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
```

## 线程池的优点

（1）提高线程的利用率

（2）提高程序的响应速度

（3）便于统一管理线程对象

（4）可以控制最大并发数