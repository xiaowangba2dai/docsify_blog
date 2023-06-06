import com.sun.org.apache.bcel.internal.classfile.InnerClass;

/**
 * 内部类
 */
public class InnerClassDemo {
    private String outerName;

    public InnerClassDemo() {
        outerName = "outer class name";
    }

    public void display() {
    }

    public static class InnerClass {
        private String innerName;

        public InnerClass() {
            innerName = "inner class name";
        }

        public void display() {
            System.out.println("innerClass");
            System.out.println(innerName);
        }
    }


    public static void main(String[] args) {
        // 创建一个正常类
        InnerClassDemo innerClassDemo = new InnerClassDemo();
        innerClassDemo.display();
        InnerClass innerClass = new InnerClass();
        innerClass.display();
    }
}
