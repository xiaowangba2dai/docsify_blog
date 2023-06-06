package object;

import java.util.Objects;

public class Student {
    private String name;
    private int no;

    public Student(String name, int no) {
        this.name = name;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return no == student.no &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, no);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", no=" + no +
                '}';
    }

    public static void main(String[] args) {
        Student student1 = new Student("wang", 1);
        Student student2 = new Student("wang",1);

        System.out.println((student1 == student2));
        System.out.println((student1.equals(student2)));
        System.out.println(student1.hashCode());
        System.out.println(student2.hashCode());
        System.out.println(student1.toString());
        System.out.println(student2.toString());

        Integer i = new Integer(2);

    }
}
