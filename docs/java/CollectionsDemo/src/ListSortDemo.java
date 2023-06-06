import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListSortDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        list.sort(new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        for (Integer o : list) {
            System.out.println(o);
        }

    }
}
