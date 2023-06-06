package communication;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWrite {
    public static void main(String[] args) {
        HashMapData t = new HashMapData();

        // 五个线程来写
        for (int i=1; i<=10; i++) {
            int finalI = i;
            new Thread(() -> {
                t.write(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }

        // 五个线程来读
        for (int i=1; i<=10; i++) {
            int finalI = i;
            new Thread(()->{
                t.read(String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }
    }
}

class HashMapData {
    private HashMap<String, String> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 写的时候，只希望同时只有一个线程写
    public void write(String key, String value) {
        readWriteLock.writeLock().lock();

        System.out.println(Thread.currentThread().getName() + "正在写: " + key + ": " + value);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写结束: " + key + ": " + value);

        readWriteLock.writeLock().unlock();
    }

    // 读的时候都可以读
    public void read(String key) {
        readWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "正在读: " + key);
        map.get(key);
        System.out.println(Thread.currentThread().getName() + "读结束: " + key);
        readWriteLock.readLock().lock();
    }

}
