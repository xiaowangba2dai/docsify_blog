package communication;

import java.util.concurrent.*;

public class B {
    public static void main(String[] args) {
        // 线程数量：停车位
        Semaphore semaphore = new Semaphore(3);

        for (int i=0; i<6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");

                    TimeUnit.SECONDS.sleep(1);

                    System.out.println(Thread.currentThread().getName() + "离开车位");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }).start();
        }

    }
}


