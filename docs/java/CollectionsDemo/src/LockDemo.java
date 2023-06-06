import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) {
        AccountDemo t = new AccountDemo();

        for (int i=0; i<10; i++) {
            new Thread(() -> {
                t.count();
            }).start();
        }
    }
}

class AccountDemo {
    private static int num;
    Lock lock = new ReentrantLock();

    public void count() {
        lock.lock();
        num++;
        System.out.println(Thread.currentThread().getName() + "获得第" + num + "个数字");
        lock.unlock();
    }
}
