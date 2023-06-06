public class TestYield implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始");
        if (""+Thread.currentThread().getState() == "线程一") {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "结束");
    }

    public static void main(String[] args) {
        TestYield t = new TestYield();
        new Thread(t, "线程一").start();
        new Thread(t, "线程二").start();
    }
}
