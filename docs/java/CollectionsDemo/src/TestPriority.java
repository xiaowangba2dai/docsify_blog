public class TestPriority implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 优先级： " + Thread.currentThread().getPriority());
    }

    public static void main(String[] args) {
        TestPriority t = new TestPriority();

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);
        Thread t5 = new Thread(t);
        Thread t6 = new Thread(t);

        // 设置线程优先级
        t1.setPriority(1);
        t2.setPriority(2);
        t3.setPriority(3);
        t4.setPriority(4);
        t5.setPriority(5);
        t6.setPriority(6);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

    }
}
