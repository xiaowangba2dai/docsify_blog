import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionDemo1 {
    public static void main(String[] args) {
        // 1. 创建一个集合
        Collection collection = new ArrayList();

        // 2. 添加元素
        collection.add("first");
        collection.add(2);
        collection.add('3');

        // 3. 遍历元素
        for (Object o : collection) {
            System.out.println(o);
        }

        // 4. 删除元素
        collection.remove(2);

        // 5. 再次遍历元素
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
