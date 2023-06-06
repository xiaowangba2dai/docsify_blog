/**
 * 泛型方法
 * 语法： <T> 返回值类型
 */
public class MyGenericMethod {
    // 泛型方法
    public <T> void show(T t) {
        System.out.println(t);
    }

    public static void main(String[] args) {
        MyGenericMethod myGenericMethod = new MyGenericMethod();
        myGenericMethod.show("String");
        myGenericMethod.show(123);
    }
}
