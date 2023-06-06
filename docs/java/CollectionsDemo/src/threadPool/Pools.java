package threadPool;

import java.util.concurrent.*;

public class Pools {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors()); // 获取CPU核数
        ExecutorService threadPools = new ThreadPoolExecutor(
                3,
                5,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()); // 哪里来去哪里

        for (int i=0; i<100; i++) {
            threadPools.execute(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }

        threadPools.shutdown();
    }
}
