public class NonLambda {
    public static void main(String[] args) {
        // 2. lambda表达式继续简化
        ILike like = () -> {
            System.out.println("lambda表达式");
        };

        like.lambda();
    }
}

// 1. 定义一个接口
interface ILike{
    void lambda();
}





