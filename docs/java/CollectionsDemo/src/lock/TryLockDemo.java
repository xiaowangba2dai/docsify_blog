package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {
    ReentrantLock lock = new ReentrantLock();

    public void getLock() throws InterruptedException {
        if (lock.tryLock(3, TimeUnit.SECONDS) == true) {
            System.out.println(Thread.currentThread().getName() + " 可以拿到锁");
            TimeUnit.SECONDS.sleep(2);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        else {
            System.out.println(Thread.currentThread().getName() + " 拿不到锁");
        }
    }
    public static void main(String[] args) {
        TryLockDemo t = new TryLockDemo();
        new Thread(()->{
            try {
                t.getLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程1").start();

        new Thread(()->{
            try {
                t.getLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程2").start();
    }
}
