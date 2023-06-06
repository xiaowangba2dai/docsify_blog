package ProducerConsumer;

/**
 * 生产者
 */
public class Producer {
    private Container container;

    public Producer(Container container) {
        this.container = container;
    }

    public void addProducts(int num) {
        for (int i=0; i<num; i++) {
            container.addProduct(new Product(i));
        }
    }

}
