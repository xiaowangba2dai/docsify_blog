import javax.swing.tree.TreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();

        map.put("1", "wang");
        map.put("2", "li");
        map.put("3", "zhang");

        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            System.out.println(s + ": " + map.get(s));
        }

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}



