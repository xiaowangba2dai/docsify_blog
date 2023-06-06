
public class TestJoin implements Runnable{
    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            System.out.println("1.子线程" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestJoin t = new TestJoin();
        Thread thread = new Thread(t);
        thread.start();

        for (int i=0; i<10; i++) {
            if (i == 5) {
                thread.join(); // 子线程强制执行
            }
            System.out.println("主线程" + i);
        }
    }
}
