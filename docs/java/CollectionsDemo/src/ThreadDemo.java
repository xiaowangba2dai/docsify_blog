/**
 * 创建线程的第一种方式
 * 1. 继承Thread类
 * 2. 重写run方法,编写线程执行体
 * 3. 创建线程对象，调用start()方法启动线程
 */
public class ThreadDemo extends Thread{
    // 线程入口点
    @Override
    public void run() {
        // 线程体
        for (int i=0; i<20; i++) {
            System.out.println("我在听课---" + i);
        }
    }

    public static void main(String[] args) {
        // 创建新线程
        ThreadDemo t = new ThreadDemo();
        // 调用run方法只有主线程一条执行路径
        // t.run();

        // 调用start方法多条执行路径，主线程和子线程并行交替执行
        t.start();
        // 主线程
        for (int i=0; i<20; i++) {
            System.out.println("我在学习多线程---" + i);
        }
    }
}
