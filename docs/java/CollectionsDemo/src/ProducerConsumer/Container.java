package ProducerConsumer;

/**
 * 产品容器类
 */
public class Container {
    private int size; // 容器大小
    private int index; // 当前大小
    private Product[] products; // 容器中的产品s

    public Container() {}

    public Container(int size) {
        this.size = size;
        products = new Product[this.size];
    }

    public synchronized void addProduct(Product product) {
        while (index >= size) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        products[index++] = product;
        System.out.println(Thread.currentThread().getName() + "添加了第" + index + "个产品");
    }

    public synchronized void removeProduct() {
        while (index <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        System.out.println(Thread.currentThread().getName() + "移走了第" + index + "个产品");
        index--;
    }

}
