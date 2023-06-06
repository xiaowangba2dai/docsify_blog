import java.util.concurrent.*;

/**
 * 创建线程的第三种方法
 * 实现Callable接口，实现call方法
 */
public class ThreadCallable implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadCallable t1 = new ThreadCallable();

        // 创建执行服务
        ExecutorService ser = Executors.newFixedThreadPool(1);

        // 提交执行
        Future<Boolean> r = ser.submit(t1);

        // 获取结果
        Boolean result = r.get();

        // 关闭服务
        ser.shutdown();
    }
}
