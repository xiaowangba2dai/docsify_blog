/**
 * 泛型类
 * 语法，类名<T>
 * T 是类型占位符，表示一种引用类型，如果编写多个使用逗号隔开
 * @param <T>
 */
public class MyGeneric<T> {
    // 1. 创建变量
    T t;

    // 2. 泛型作为方法的参数
    public void show (T t) {
        System.out.println(t);
    }

    // 3. 泛型作为方法的返回值
    public T getT() {
        return t;
    }
}
