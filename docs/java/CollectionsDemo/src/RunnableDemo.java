/**
 * 创建线程的第二种方式：实现Runnable接口
 * 实现run()方法，编写线程执行体
 * 创建线程对象，调用start()方法启动线程
 */
public class RunnableDemo implements Runnable{
    @Override
    public void run() {
        for (int i=0; i<20; i++) {
            System.out.println("2 子线程-----" + i);
        }
    }

    public static void main(String[] args) {
        RunnableDemo t = new RunnableDemo();
        new Thread(t).start();

        for (int i=0; i<20; i++) {
            System.out.println("1 主线程----"  + i);
        }
    }
}
