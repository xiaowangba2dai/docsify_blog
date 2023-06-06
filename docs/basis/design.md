## 创建型模式
创建型模式对类的实例化过程进行了抽象，能够将对象的创建与对象的使用过程分离。

### 简单工厂模式
简单工厂模式，可以根据参数的不同返回不同类的实例。

简单工程模式专门定义一个类来负责创建其他类的实例，被创建的实例通常都具有共同的父类。

![../_images/SimpleFactory.jpg](https://design-patterns.readthedocs.io/zh_CN/latest/_images/SimpleFactory.jpg)

```java
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

```

优点：实现对象的创建和使用分离。创建完全交给工厂类
缺点：工厂类不够灵活，新增一个产品就要修改工厂类的判断逻辑，这一点与开闭原则是相违背的。

适用环境：
工厂类负责创建的对象比较少；由于创建的对象较少，不会造成工厂方法中的业务逻辑太过复杂。
客户端只知道传入工厂类的参数，对于如何创建对象不关心。

### 工厂方法模式
又称工厂模式。
工厂父类负责定义创建产品对象的公共接口，而工厂子类则负责生成具体的产品对象。
这样做的目的是将产品类的实例化操作延迟到工厂子类中完成，即通过工厂子类来确定究竟应该实例化哪一个具体对象。

![../_images/FactoryMethod.jpg](https://design-patterns.readthedocs.io/zh_CN/latest/_images/FactoryMethod.jpg)

```java
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
```
`优点：`用户只需关心所需产品对应的工厂，无须关心创建细节，甚至无需知道具体产品类的类名。

在系统中加入新产品时，无需修改抽象工厂和抽象产品提供的接口，只要添加一个具体工厂和具体产品就可以了，这样，系统的可扩展性也就变的非常好，完全符合“开闭原则”。

`缺点：`
添加新产品时，需要编写新的具体产品类，还要提供与之对应的具体工厂类，增加了系统的复杂度。

