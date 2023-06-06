package designModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式，构造器私有，一个类只能有一个实例对象
 */
public class SingleDemo {

    private volatile static SingleDemo singleDemo;
    private static boolean biaozhi = false;

    private SingleDemo () {
        synchronized (SingleDemo.class) {
            if (biaozhi == true) {
                throw new RuntimeException("被我的标志位限制了");
            }
            if (biaozhi == false) {
                biaozhi = true;
            }
            if (singleDemo != null) {
                throw new RuntimeException("不要试图使用反射");
            }
        }
        System.out.println(("构造方法"));
    }

    public static SingleDemo getInstance() {
        // 双重检测模式
        if (singleDemo == null) {
            synchronized (SingleDemo.class) {
                if (singleDemo == null) {
                    singleDemo = new SingleDemo();
                }
            }
        }

        return singleDemo;
    }

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        SingleDemo instance = SingleDemo.getInstance();
        // 利用反射暴力破解
        Class<? extends SingleDemo> aClass = SingleDemo.class;
        Constructor<? extends SingleDemo> declaredConstructor = aClass.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true); // 无视私有构造器
        SingleDemo singleDemo1 = declaredConstructor.newInstance();

        // 把标志位再次设为false
        Field biaozhi = aClass.getDeclaredField("biaozhi");
        biaozhi.set(singleDemo1,false);
        SingleDemo singleDemo2 = declaredConstructor.newInstance();

        System.out.println((singleDemo1 == singleDemo2)); // false, 两个不一样
    }
}
