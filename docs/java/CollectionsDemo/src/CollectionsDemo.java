import java.util.ArrayList;
import java.util.Collections;

public class CollectionsDemo {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(30);
        list.add(13);
        list.add(70);

        Collections.reverse(list);
        for (Integer e : list) {
            System.out.println(e);
        }
    }
}
