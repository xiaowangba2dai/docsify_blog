import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class StaticProxy {
    public static void main(String[] args) {
        MarryCompary marryCompary = new MarryCompary(new People());
        marryCompary.marry();
    }
}

// 结婚接口
interface Marry {
    public void marry();
}

// 我要结婚，实现结婚接口
class People implements Marry {
    @Override
    public void marry() {
        System.out.println("结婚中");
    }
}

// 婚庆公司代理你结婚，实现结婚接口
class MarryCompary implements Marry {

    private Marry marry;

    public MarryCompary(Marry marry) {
        this.marry = marry;
    }

    @Override
    public void marry() {
        before();
        marry.marry();
        after();
    }

    public void before() {
        System.out.println("结婚前");
    }
    public void after(){
        System.out.println("结婚后");
    }
}


