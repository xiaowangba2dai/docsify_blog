package ProducerConsumer;

public class Model {
    public static void main(String[] args) {
        Container container = new Container(20);
        Producer producer = new Producer(container);
        Consumer consumer = new Consumer(container);
        new Thread(()->producer.addProducts(30)).start();
        new Thread(()->consumer.removeProducts(30)).start();
    }
}
