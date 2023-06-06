import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List 子接口的使用
 */

public class ListDemo {
    public static void main(String[] args) {
        // 先创建集合对象
        List list = new ArrayList<>();

        // 添加元素
        list.add("111");
        list.add(222);
        list.add("333");

        // 迭代元素, 方法一
        System.out.println("the first print:");
        for (Object o : list) {
            System.out.println(o);
        }

        // 删除元素
        list.remove(1);

        // 迭代元素, 方法二
        System.out.println("the second print:");
        for (int i=0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 删除元素
        list.remove("111");

        // 迭代元素, 方法三
        System.out.println("the third print:");
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // 迭代元素，方法四
        System.out.println("the fourth print:");
        ListIterator listIterator = list.listIterator();

        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous());
        }

        // 获取位置
        int index = list.indexOf("333");
        System.out.println(index);


    }
}
