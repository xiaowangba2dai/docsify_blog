import java.util.*;

public class Baidu1 {
    public static void method(String str) {
        // aabbbccccdddddeeghik
        HashMap<Character, Integer> map = new HashMap<>();

        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            map.put(c, map.getOrDefault(c, 0)+1);
        }

        PriorityQueue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        Set<Map.Entry<Character,Integer>> sets = map.entrySet();
        for (Map.Entry<Character,Integer> set : sets) {
            queue.add(set);
        }

        while (!queue.isEmpty()) {
            System.out.println(queue.poll().getValue());
        }
    }

    public static void main(String[] args) {

       method("aabbbccccdddddeeghik");
    }
}
