package designModel.Factory;

abstract class Product1 {
    public abstract void print1();
}

class ProductA1 extends Product1 {
    @Override
    public void print1() {
        System.out.println("ProductA1");
    }
}

class ProductB1 extends Product1 {
    @Override
    public void print1() {
        System.out.println("ProductB1");
    }
}

abstract class Factory {
    public abstract Product1 createProduct();
}

class FactoryA1 extends Factory {
    @Override
    public Product1 createProduct() {
        return new ProductA1();
    }
}

class FactoryB1 extends Factory {
    @Override
    public Product1 createProduct() {
        return new ProductB1();
    }
}

public class FactoryMethod {
    public static void main(String[] args) {
        Product1 p1 = new FactoryA1().createProduct();
        p1.print1();
        Product1 p2 = new FactoryB1().createProduct();
        p2.print1();
    }
}
