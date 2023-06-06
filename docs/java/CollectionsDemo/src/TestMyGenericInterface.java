public class TestMyGenericInterface implements MyGenericInterface<String>{
    @Override
    public String server(String s) {
        return s;
    }

    public static void main(String[] args) {
        TestMyGenericInterface testMyGenericInterface = new TestMyGenericInterface();
        System.out.println(testMyGenericInterface.server("better"));
    }
}
