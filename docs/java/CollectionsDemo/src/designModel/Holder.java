package designModel;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class Holder {
    private Holder(){
        System.out.println(("构造方法"));
    }

    // 静态内部类
    private static class InnerClass {
        // 在内部构造外部类
        private static final Holder HOLDER = new Holder();
    }

    // 提供一个静态方法返回对象
    public static Holder getInstance() {
        return InnerClass.HOLDER;
    }

    public static void main(String[] args) {
        for (int i=0; i<100; i++) {
            new Thread(()->{
                Holder.getInstance();
            }).start();
        }
    }
}
