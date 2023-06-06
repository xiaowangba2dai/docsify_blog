package tickets;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTickets {
    private static int nums = 15;
    private static int selled = 0;
    private Lock lock = new ReentrantLock();

    public void sell() {
        while (true) {
            if (selled >= 15) {
                return;
            }
            lock.lock();
            lock.lock();
            selled++;
            System.out.println(Thread.currentThread().getName() + "卖出了第" + selled + "张票");
            lock.unlock();
            lock.unlock();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        ThreadTickets t = new ThreadTickets();
        new Thread(()->t.sell(), "小明").start();
        new Thread(()->t.sell(), "小红").start();
        new Thread(()->t.sell(), "小竹").start();
    }
}
