package rewrite;

public class Father {
    public static void main(String[] args) {
        // 1. 基本类型，只能用 ==, 没有equal, 比较的是值
        int a = 1;
        int b = 1;

        System.out.println((a == b)); // true

        // 2. 引用类型
        String s1 = "11"; // 放在堆中常量池
        String s2 = "11"; // 指向上面的常量池
        String s3 = new String("11");

        System.out.println(s1 == s2); // true, 等号是判断两个实例是不是指向同一个内存空间
        System.out.println((s1 == s3)); // false

        System.out.println((s1.equals(s2))); // true
        System.out.println(s1.equals(s3)); // true, String的equals方法比较的是字符串的值是否相等

        // 3. Integer
        Integer a1 = 1;
        Integer a2 = 1;
        Integer a3 = new Integer(1);

        System.out.println((a1 == a2)); // true
        System.out.println((a1 == a3)); // false

        System.out.println((a1.equals(a2))); // true
        System.out.println((a1.equals(a3))); // true

    }
}


