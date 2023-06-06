import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class TestState {
    public static void main(String[] args) throws InterruptedException {
        // 先建一个新的线程，用lambda表达式
        Thread thread = new Thread(()->{
            for (int i=0; i<5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("new 之后的状态：" + thread.getState());

        thread.start();
        System.out.println("start之后的状态:" + thread.getState());

        // 只要线程不停止，不断输出线程的状态
        while (thread.getState() != Thread.State.TERMINATED) {
            Thread.sleep(100);
            System.out.println(thread.getState());
        }


    }
}
