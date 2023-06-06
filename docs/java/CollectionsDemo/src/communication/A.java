package communication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程通信，synchronized 的 wait 和 nofify方法
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(()->{
            data.printa();
        }, "A").start();

        new Thread(()->{
            data.printb();
        }, "B").start();


        new Thread(()->{
            data.printc();
        },"C").start();

    }
}

class Data { // 资源类，独立
    private int num;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    // num = 0:  a线程执行+1
    public void printa() {
       lock.lock();
        try {
            if (num != 0) {
                condition1.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "处理num: " + num);
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // num = 1:  b线程执行+1
    public void printb() {
        lock.lock();
        try {
            if (num != 1) {
                condition2.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "处理num: " + num);
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // num = 2:  c线程执行+1
    public void printc() {
        lock.lock();
        try {
            if (num != 2) {
                condition3.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + "处理num: " + num);
            condition3.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
