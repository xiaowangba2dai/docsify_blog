public class NotStatic {
    public static void main(String[] args) {
        Data data = new Data();

        // 锁定的是方法的调用者,data
        // 这里只有一个data
        new Thread(()->{
            data.fun1();
        }, "线程一").start();

        new Thread(()->{
            data.fun2();
        }, "线程二").start();
    }
}
class Data {

    public synchronized void fun1(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1...");
    }

    public synchronized void fun2(){
        System.out.println("2...");
    }
}