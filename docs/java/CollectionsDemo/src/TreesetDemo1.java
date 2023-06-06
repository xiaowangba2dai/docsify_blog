import java.util.Comparator;
import java.util.TreeSet;

/**
 * TreeSet 存储复杂类型
 */

public class TreesetDemo1 {
    public static void main(String[] args) {
        TreeSet<Student> treeSet = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                int n1 = o1.getName().compareTo(o2.getName());
                int n2 = o1.getAge() - o2.getAge();
                return n1 == 0 ? n2 : n1;
            }
        });

        Student student1 = new Student("小王", 18);
        Student student2 = new Student("小李", 20);
        Student student3 = new Student("小张", 30);

        treeSet.add(student1);
        treeSet.add(student2);
        treeSet.add(student3);

        treeSet.add(new Student("小张", 30));

        for (Student student : treeSet) {
            System.out.println(student.toString());
        }
    }
}
