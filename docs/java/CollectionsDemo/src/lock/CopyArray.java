package lock;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class CopyArray {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i=0; i<10; i++) {
            new Thread(()->{
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("a");
                System.out.println(list);
            }).start();
        }
    }
}
