import java.util.Comparator;
import java.util.TreeSet;

/**
 * TreeSet 实现字符串按照长度进行排序
 * helloworld zhang lisi wangwu beijing xian nanjing
 */

public class TreesetDemo2 {
    public static void main(String[] args) {
        TreeSet<String> treeSet = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        treeSet.add("helloworld");
        treeSet.add("zhang");
        treeSet.add("lisi");

        for (String s : treeSet) {
            System.out.println(s);
        }
    }
}
