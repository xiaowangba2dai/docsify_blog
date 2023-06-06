/**
 * 多个线程同时操作同一个对象
 * 买火车篇的例子
 *
 * 问题：线程不安全
 */
public class TestThread implements Runnable{
    private int ticketNum = 10;

    @Override
    public void run() {
        while (true) {
            if (ticketNum <= 0) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "买了第" + ticketNum + "张票");
            ticketNum--;
        }
    }

    public static void main(String[] args) {
        TestThread t = new TestThread();
        new Thread(t).start();
        new Thread(t).start();
        new Thread(t).start();
    }
}
