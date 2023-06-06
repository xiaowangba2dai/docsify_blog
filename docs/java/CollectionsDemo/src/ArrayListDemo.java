import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListDemo {
    public static void main(String[] args) {
        // 创建
        ArrayList arrayList = new ArrayList<>();

        // 添加
        Student student1 = new Student("小王", 18);
        Student student2 = new Student("小李", 20);
        Student student3 = new Student("小张", 30);

        arrayList.add(student1);
        arrayList.add(student2);
        arrayList.add(student3);

        // 遍历
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // 删除
        arrayList.remove(new Student("小王", 18));
        System.out.println(arrayList.size());

        // 判断
        System.out.println(arrayList.contains(student2));

        // 查找
    }
}
