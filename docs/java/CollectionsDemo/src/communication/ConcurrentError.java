package communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcurrentError {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i=1; i<=100; i++) {
            int finalI = i;
            new Thread(()->{
//                try {
//                    TimeUnit.MILLISECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                map.put(finalI, finalI);
                System.out.println(map.get(finalI));
            }).start();
        }
    }
}
