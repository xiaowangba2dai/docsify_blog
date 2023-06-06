package queue;

import java.sql.Time;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SyncQueue {
    public static void main(String[] args) {
        SynchronousQueue synchronousQueue = new SynchronousQueue();

        // 一个线程负责加元素
        new Thread(()->{
            try {
                synchronousQueue.put("1");
                System.out.println("jia 1");
                synchronousQueue.put("2");
                System.out.println(("jia 2"));
                synchronousQueue.put("3");
                System.out.println(("jia 3"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 一个线程负责取元素
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("take");
                synchronousQueue.take();
                TimeUnit.SECONDS.sleep(1);
                System.out.println("take");
                synchronousQueue.take();
                TimeUnit.SECONDS.sleep(1);
                System.out.println("take");
                synchronousQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
