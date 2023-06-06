/**
 * 线程不安全之买票例子
 */
public class UnsafeBuyTicket implements Runnable{
    // 票
    private int ticketNums = 20;

    @Override
    public void run() {
        while (ticketNums > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buy();
        }
    }

    public synchronized void buy() {
        System.out.println(Thread.currentThread().getName() + "买了第" + ticketNums-- + "张票");
    }

    public static void main(String[] args) {
        UnsafeBuyTicket t = new UnsafeBuyTicket();
        new Thread(t, "小红").start();
        new Thread(t, "小明").start();
        new Thread(t, "小黑").start();
    }
}
