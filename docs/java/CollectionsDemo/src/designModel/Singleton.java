package designModel;

/**
 * 饿汉式：一上来就创建对象，很饿
 */
public class Singleton {
    private static Singleton sin = new Singleton();

    private Singleton(){
    }

    public static Singleton getInstance() {
        return sin;
    }
}
