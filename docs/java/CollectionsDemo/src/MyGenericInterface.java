/**
 * 泛型接口
 * 语法：接口名<T>
 */

public interface MyGenericInterface<T> {
    String name = "wang";
    T server(T t);
}
