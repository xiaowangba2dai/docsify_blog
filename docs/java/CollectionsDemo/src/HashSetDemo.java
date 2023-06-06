import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<Student> hashSet = new HashSet<>();

        Student student1 = new Student("小王", 18);
        Student student2 = new Student("小李", 20);
        Student student3 = new Student("小张", 30);

        hashSet.add(student1);
        hashSet.add(student2);
        hashSet.add(student3);
        hashSet.add(new Student("小王", 18));

        for (Student o : hashSet) {
            System.out.println(o.toString());
        }
    }
}
