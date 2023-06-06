public class Account implements Runnable{

    private int num;

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            num++;
            System.out.println(num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();
        for (int i=0; i<100; i++) {
            new Thread(account).start();
        }
    }
}
