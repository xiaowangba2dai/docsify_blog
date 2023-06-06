public class StaticSync {
    public static void main(String[] args) {
        Data1 data1 = new Data1();
        for (int i=0; i<5; i++) {
            new Thread(()->{
                data1.func();
            }).start();
        }
    }
}

class Data1 {
    public void func(){
        // 锁定的是this对象
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end...");
        }
    }
}