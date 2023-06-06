/**
 * 停止线程的推荐方法
 * 使用一个标志位进行终止
 */
public class StopThread implements Runnable {
    // 1. 线程中定义线程体使用的标识
    private boolean flag = true;

    @Override
    public void run() {
        // 2. 线程体使用该标识
        while (flag) {
            System.out.println("run");
        }
    }

    // 3. 对外提供方法改变标识
    public void stop(){
        this.flag = false;
    }
}
