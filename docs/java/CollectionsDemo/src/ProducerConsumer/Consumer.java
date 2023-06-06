package ProducerConsumer;

public class Consumer {
    private Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    public void removeProducts(int num) {
        for (int i=0; i<num; i++) {
            container.removeProduct();
        }
    }

}
