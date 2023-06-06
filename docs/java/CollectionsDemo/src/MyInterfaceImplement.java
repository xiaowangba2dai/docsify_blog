public class MyInterfaceImplement{
    public static void main(String[] args) {
        // 匿名内部类是接口的实现类
        MyInterface myInterface = new MyInterface() {
            @Override
            public void test() {
                System.out.println("imple...");
            }
        };

        myInterface.test();
    }
}
