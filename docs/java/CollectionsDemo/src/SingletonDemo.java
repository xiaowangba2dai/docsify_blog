/**
 * 单例模式：一个类只能有一个实例化对象
 */
public class SingletonDemo{

    // 创建一个静态的成员变量
    private volatile static SingletonDemo singletonDemo;

    // 私有构造函数
    private SingletonDemo() {
        System.out.println("走进了构造函数");
    }

    // 没有必要把整个锁起来
    public static SingletonDemo getInstance() {
        if (singletonDemo == null) {
            synchronized (SingletonDemo.class) {
                if (singletonDemo == null) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }


    public static void main(String[] args) {
        for (int i=0; i<100; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            }).start();
        }
    }
}
