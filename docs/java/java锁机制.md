在Java中，每个对象都拥有一把锁。
这把锁存在对象头的Mark Work中，锁中记录了当前对象被哪个线程所占用。

## 对象结构
![image-20210407091213709](C:\Users\23752\AppData\Roaming\Typora\typora-user-images\image-20210407091213709.png)

（1）对象头：存放了一些对象运行时的信息

1. Mark Word（只有32bit）：存储了很多和当前对象运行时状态有关的数据，比如说hashcode、锁状态标志、指向所记录的指针等等。

   ![image-20210407091700637](C:\Users\23752\AppData\Roaming\Typora\typora-user-images\image-20210407091700637.png)

   synchronized的同步机制可能存在性能问题，因为synchronized被编译之后实质上是monitorenter和monitorexit两个字节码指令，而monitor是依赖于操作系统的mutex lock来实现的。

   Java线程实际上是对操作系统线程的映射，所以每当挂起或者唤醒一个线程都要切换操作系统内核态，这种操作是比较重量级的。在一些情况下甚至切换时间本身将会超出线程执行任务的时间。

   从Java6 开始，synchronized进行了优化，引入了偏向锁、轻量级锁。

   

2. Class Point：一个指针，指向了当前对象类型所在方法区中的类型数据

（2）实例数据：属性、方法

## 无锁、偏向锁、轻量级锁、重量级锁
**（1）无锁**

顾名思义就是没有对资源进行锁定，所有线程都能访问到统一资源。

**适用场景**：

1. 无竞争：某个对象不会出现在多线程环境下，或者即使出现在了多线程环境下也不会出现竞争的情况。

2. 存在资源：资源会被竞争但是我不想对资源进行锁定。不过还是想通过一些机制来控制多线程。比如说，如果有多个线程想要修改同一个值，我们不通过锁定资源的方式，而是通过其他方式来限制同时只有一个线程能够修改成功，而其他修改失败的线程将会不断重试，直到修改成功，这就是著名的`CAS（Compare and Swap）`。

   CAS在操作系统中通过一条指令来实现，所以它能够保证原子性。

**（2）偏向锁**（只有一个线程去申请）

大部分情况下，无锁的效率是很高的。

但这并非意味着，无锁能够全面代替有锁。

假如一个对象被加锁了，实际运行时只有一个线程会获取这个对象锁。

没有其他线程去争抢这个锁。它就没有必要去释放这个锁了。

最理想的方式是不通过线程状态切换、也不要通过CAS来获取锁。因为多多少少还是会耗费一些资源。

`我们设想，最好对象能够认识这个线程。只要这个线程过来，那么对象就直接把锁交出去。`

我们认为这个锁偏爱这个线程。所以称为偏向锁。

**偏向锁如何实现**

在Mark Word中，当锁标志位为01时，判断倒数第三个bit是否为1。

如果是1，那么代表当前对象的锁状态为偏向锁。否则则为无锁。

如果当前锁状态为偏向锁，于是再去读前23个bit，也就是线程ID。

通过线程ID来确认线程。

**（3）轻量级锁**（两个线程去争夺这个对象）

假如不只有一个线程，而是有多个线程竞争锁。

那么偏向锁将会升级为轻量级锁。

**如何判断线程和锁之间的绑定关系**

将Mark Word前30个bit变成`指向线程栈中锁记录的指针`

当一个线程想要获得某个对象的锁时，假如看到锁标志位为00那么就知道它是轻量级锁。

这是线程会在自己的虚拟机栈中开辟一块被称为Lock Record的空间。

Lock Record中存放的是对象头的Mark Word的副本以及owner指针。

线程通过CAS区尝试获取锁，一旦获得将会复制该对象头中的Mark Word 到Lock Record中，并且将Lock Record中的owner指针指向该对象。

另一方面，对象的Mark Word中的前30bit将会生成一个指针，指向线程虚拟机栈中的Lock Record。

这样就实现了线程和对象锁的绑定。

**这时候万一有其他线程也要获取这个对象怎么办？**

此时其他线程将会自旋等待，自旋可以理解为一种轮询。

这种方式区别于被操作系统挂起，因为如果对象的锁很快就被释放的话，自旋就不需要进行系统中断和现场恢复，所以它的效率更高。

但是长时间自旋将会浪费CPU资源，于是出现了一种叫做“适应性自旋的优化”，简单来说，就是自旋的时间不固定。比如说，在同一个锁上，当前正在自旋等待的线程，刚刚已经成功获得过锁，但是锁目前是被其他线程占用，那么虚拟机就会认为这次自旋成功率高，进而允许更长的自旋时间。

**（4）重量级锁**

如果同时有多个线程想要获得这个对象锁，一旦自旋等待的线程数超过一个，那么轻量级锁就会升级为重量级锁。

如果标志位是重量级锁，那么需要通过Monitor来对线程进行控制，此时将会完全锁定资源，对线程的管控最为严格。

## 了解CAS吗，谈谈你对CAS理解

假如有多个线程申请同一个资源对象。

第一反应使用`互斥锁（悲观锁）`,  互斥锁的同步方式是悲观的。操作系统将会悲观地认为，如果不严格同步线程调用，将会出现异常。所以互斥锁将会锁定资源，其他线程将阻塞。

悲观锁不是万能的，比如大部分线程都是读操作，那么就没有必要在每次调用地时候都锁定资源，或者同步代码块执行的耗时远远小于线程切换的耗时。

假设资源对象有一个状态值，0代表空闲，1代表被占用。

当资源对象为0的一瞬间，ab线程都读到了，此时这两条线程认为资源对象当前的状态值为0，于是他们会各自产生两个值，old value代表之前读到的资源对象的状态值，new value代表想要将资源对象的状态值更新后的值。

这里对ab线程来说，old value都是0，new value都是1.

![image-20210407105205465](C:\Users\23752\AppData\Roaming\Typora\typora-user-images\image-20210407105205465.png)

此时ab线程争抢着去修改资源对象的状态值，然后占用它。

假设a线程率先获得时间片，它将old value与资源对象的状态值进行compare，发现一致，于是将牌子上的值swap为new value。

而b线程没有那么幸运，它落后了一步。此时资源状态值已经被修改了，b线程自旋等待。

**CAS分为compare和swap两步操作，多线程下难以保证一致性？**

当a看到了牌子的状态是0时，伸手去翻牌子的一瞬间，很有可能b线程突然抢到时间片，将牌子翻成1。但a不知道，也将牌子翻到了1。

ab线程同时获得了资源？

**核心问题**：比较数值是否一致，并且翻牌的这个动作必须同时只能有一条线程进行操作。

CAS必须是原子性的。各种不同架构的CPU都提供了指令级别的CAS原子操作。

不需要经过操作系统的同步原语，CPU已经原生的支持了CAS，这样我们就可以不再依赖锁来进行线程同步。

由于不会锁定资源，当线程需要修改共享资源的对象时，总是会乐观的认为对象状态值没有被其他线程修改过，每次都会自己主动尝试compare状态值。

**简单的例子：使用3条线程，将一个值，从0累加到1000**

首先演示不加同步机制的错误做法：

```java
public class casTest {

    static Integer num = 0;

    public static void main(String[] args) {
        for (int i=0; i<3; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (num < 1000) {
                        System.out.println("thread name: " + Thread.currentThread().getName() + ": " + num++);
                    }
                }
            });
            t.start();
        }
    }
}

```

通过互斥锁改进：

```java
public class casTest {

    static Integer num = 0;

    public static void main(String[] args) {
        for (int i=0; i<3; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (Math.class) {
                        while (num < 1000) {
                            System.out.println("thread name: " + Thread.currentThread().getName() + ": " + num++);
                        }
                    }
                }
            });
            t.start();
        }
    }
}

```

使用乐观锁改进：

```java
import java.util.concurrent.atomic.AtomicInteger;

public class casTest {

    static AtomicInteger num = new AtomicInteger(0);
    // 底层通过CAS实现同步的计数器

    public static void main(String[] args) {
        for (int i=0; i<3; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (num.get() < 1000) {
                        System.out.println("thread name: " + Thread.currentThread().getName() + ": " + num.incrementAndGet());
                    }
                }
            });
            t.start();
        }
    }
}

```



## 用过AQS吗，用具体的例子可以说说吗
## 看过JUC的源码吗，聊聊具体的实现吧

## 锁用在静态方法和非静态方法上面有什么区别？
静态方法锁的class
非静态方法锁的this对象

## ReentrantLock 重入锁，和synchronized有什么区别

**一句话概括**：
ReentrantLock基于AQS，在并发编程中它可以实现公平锁和非公平锁来对共享资源进行同步，同时，和synchronized一样，ReentrantLock支持可重入。除此之外，ReentrantLock在调度上更灵活，支持更多丰富的功能。

![image-20210407143642219](C:\Users\23752\AppData\Roaming\Typora\typora-user-images\image-20210407143642219.png)

**可重入性**：一个线程可以不用释放锁，而重复获取一个锁

场景：A线程在某上下文中获取了某锁，当A线程想要再次获取该锁时，即A线程既获取了锁，又在等待自己释放锁。

**公平锁：**

按照请求锁的顺序分配，拥有稳定获得锁的机会，但是性能可能比非公平锁低。

比如AQS中介绍的FIFO队列，就能实现公平锁。

**非公平锁：**

不按照请求锁的顺序分配，不一定拥有获得锁的机会，但是性能可能比公平锁高。