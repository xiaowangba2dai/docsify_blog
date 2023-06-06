package queue;

import java.sql.Time;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(arrayBlockingQueue.offer("1", 1, TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.offer("2", 1, TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.offer("3", 1, TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.offer("4", 1, TimeUnit.SECONDS)); // 等待一秒，返回false

        System.out.println(arrayBlockingQueue.poll(1,TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.poll(1,TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.poll(1,TimeUnit.SECONDS));
        System.out.println(arrayBlockingQueue.poll(1,TimeUnit.SECONDS));  // 等待一秒, 返回null
    }
}
