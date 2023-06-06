package designModel.Factory;

abstract class Product {
    public abstract void print();
}

class ProductA extends Product {
    @Override
    public void print() {
        System.out.println(("ProductA print"));
    }
}

class ProductB extends Product {
    @Override
    public void print() {
        System.out.println(("ProductB print"));
    }
}

//
public class SimpleFactory {
    public static Product createProduct(String type) {
        if (type.equals("A")) {
            return new ProductA();
        }
        else {
            return new ProductB();
        }
    }

    public static void main(String[] args) {
        Product product = SimpleFactory.createProduct("A");
        product.print();
    }
}


