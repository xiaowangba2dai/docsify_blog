package reflect;

import domain.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectDemo1 {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        System.out.println(("获取Class类对象的三种方法"));
        // 1. 第一种方式，多用于配置文件中读取文件，加载类
        Class cls1 = Class.forName("domain.Student");
        System.out.println(cls1);

        // 2. 第二种方式，多用于参数的传递
        Class cls = Student.class;
        System.out.println(cls);

        // 3. 第二种方式，多用于对象的字节码获取方式
        Student s = new Student("wang", 1);
        Class cls3 = s.getClass();
        System.out.println(cls3);

        // 三个对象是否是同一个
        // 同一个字节码文件在一次程序运行过程中只被加载一次
        // 每一个字节码文件对应的Class类对象都不相同
        System.out.println((cls1 == cls));
        System.out.println((cls == cls3));
        System.out.println(("------------"));

        // 1. 获取成员变量们
        System.out.println(("获取fields(public)"));
        Field[] fields = cls.getFields();
        for (Field field : fields) {
            System.out.println((field));
        }

        System.out.println(("------------"));
        System.out.println(("获取fields(all)"));
        Field[] fields1 = cls.getDeclaredFields();
        for (Field field : fields1) {
            System.out.println((field));
        }

        System.out.println(("------------"));

        // 获取方法
        System.out.println(("获取methods(public)"));
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        System.out.println(("------------"));

        System.out.println(("获取methods(all)"));
        Method[] methods1 = cls.getDeclaredMethods();
        for (Method method : methods1) {
            System.out.println(method);
        }
        System.out.println(("------------"));


        // 执行方法
        Method getName = cls.getMethod("getName");
        Object invoke = getName.invoke(s);
        System.out.println(invoke);

        // 获取所有方法
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method);
        }
    }
}
