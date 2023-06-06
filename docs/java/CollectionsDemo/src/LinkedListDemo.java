import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList<>();

        Student student1 = new Student("小王", 18);
        Student student2 = new Student("小李", 20);
        Student student3 = new Student("小张", 30);

        // 添加
        linkedList.add(student1);
        linkedList.add(student2);
        linkedList.add(student3);

        System.out.println(linkedList.size());
        System.out.println(linkedList);

        // 删除
        linkedList.remove(student2);
        linkedList.remove(new Student("小王", 18)); // 需要重写Studnet类的equals方法
        System.out.println(linkedList);

        // 迭代
        Iterator iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for (Object e : linkedList) {
            System.out.println(e);
        }

        for (int i=0; i<linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }

        // 判断
        System.out.println(linkedList.contains(student2));

        // 获取位置
        System.out.println(linkedList.indexOf(student3));
    }
}
