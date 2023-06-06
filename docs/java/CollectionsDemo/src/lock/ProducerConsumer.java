package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者与消费者模式
 */
public class ProducerConsumer {
    private static int num;
    private static int size = 5;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void produce() {
        lock.lock();
        while (num >= size) {
            try {
                condition.await(); // 满了，让当前正在访问这个资源得线程休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        num++;
        condition.signalAll();
        System.out.println(Thread.currentThread().getName() + " 生产了第 " + num + " 个产品");
        lock.unlock();
    }

    public void consume() {
        lock.lock();
        while (num <= 0) {
            try {
                condition.await(); // 空的，让当前正在访问这个资源得线程休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 消费了第 " + num + " 个产品");
        num--;
        condition.signalAll();
        lock.unlock();
    }

    public static void main(String[] args) {
        ProducerConsumer t = new ProducerConsumer();
        new Thread(()->{
            for (int i=0; i<30; i++) {
                t.produce();
            }
        },"生产者").start();

        new Thread(()->{
            for (int i=0; i<30; i++) {
                t.consume();
            }
        },"消费者").start();
    }



}
