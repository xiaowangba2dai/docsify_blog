## 进程和线程
线程由进程创建，共享进程的资源，轻量级

## 多线程的创建方式
（1）继承Thread类，重写Thread的run方法
（2）实现Runnable接口
（3）实现Callable接口
（4）线程池



```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test{

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. Thread, 重写run方法
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println(10);
            }
        };
        thread.start();

        // 2. 也是Thread,实现Runnable接口，实现它的run方法
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(123);
            }
        });
        thread1.start();

        // 3. 实现Callable的call方法, 有返回值
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 11;
            }
        };

        // 封装成任务
        FutureTask<Integer> task = new FutureTask<>(callable);

        new Thread(task).start();

        while (task.isDone()) {
            System.out.println(task.get());
        }
    }
}
```

## Runnable 和 Callable
**Callable:** 可以返回结果值，需要在定义时执行类型

##  线程的六种状态
（1）NEW：初始化态，`Thread thread = new Thread()`;
（2）RUNNABLE：`thread.start()`;
	Ready: 准备态
	Running:执行态
（3）BLOCKED：阻塞态，拿不到锁，只能在外面等。
（4）WAITING：等待态，拿到锁之后，发现当前执行条件不满足，需要暂时停止执行，让出CPU,`thread.wait()`
（5）TIMED_WAITING：超时等待态，等个固定时间，`thread.wait(2000)`
（6）TERMINATED：结束态

## sleep和wait的区别
Sleep:
	暂停当前线程执行，但是不释放锁
	Thread对象的
	可以在任何场景下使用
	只有睡够时间才能醒
Wait:
	暂停当前线程执行，释放锁
	Object对象的
	只能在同步块中使用
	可以随时唤醒

## 生产者/消费者模型
```java
import java.util.LinkedList;
import java.util.Queue;

class Producer extends Thread {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            try {
                buffer.add(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i=0; i<10; i++) {
            int val = 0;
            try {
                val = buffer.pull();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(val);
        }
    }
}

class Buffer {

    private Queue<Integer> queue = new LinkedList<>();
    private int size = 5;

    public synchronized void add(int val) throws InterruptedException {
        if (queue.size() > size) {
            wait(); // 阻塞生产者
        }
        queue.add(val);
        notify(); // 通知消费者
    }

    public synchronized int pull() throws InterruptedException {
        if (queue.size() == 0) {
            wait();
        }
        int val = queue.poll();
        notify();
        return val;
    }
}


public class Test{

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
    }
}
```

## 线程的优先级越高越先执行吗？
不一定。

原因：
	（1）交给操作系统调度，优先级由操作系统决定
	java优先级：1-10，win系统只有7级
	（2）系统会自己改变优先级，win存在优先级推进器的功能。
	（3）优先级只是倾向性，不能保证一定，调度策略导致的不确定性。

## 可见性 Volatile（轻量级）

**（1）什么是可见性**

我们进行了一些写操作，在另一些地方进行读操作，`能立即读到写后的最新值`。

**（2）多线程中的可见性问题**

线程一写操作，线程二不能立刻读到主存中的最新值。

```java
public class Test {
    static boolean a = true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
           while(a){} // 读操作，a被写为false,但是不可见，一直读的是CPU的缓存这里仍然是true
        }).start();

        Thread.sleep(1000);
        a = false; // 写操作
        System.out.println(a);
    }
}
```
**加一行代码，给了线程喘息空间，能够去主存中拿最新的值**

```java
public class Test {
    static boolean a = true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
           while(a){
               try {
                   Thread.sleep(1); // 加一行代码
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           } 
        }).start();

        Thread.sleep(1000);
        a = false; // 写操作
        System.out.println(a);
    }
}
```

**volatile关键字**

**功能：**
	保证数据的可见性，即变量在多个线程间可见，解决了`脏读问题`

**误区：**
	只能保证可见性，并不能保证原子性。

```java
public class Test {
    static volatile boolean a = true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
           while(a){
           }
        }).start();

        Thread.sleep(1000);
        a = false; // 写操作
        System.out.println(a);
    }
}
```

## 原子性 Atom

线程1、线程2都是先读后写操作，比如都是i++操作，最后i可能只加了一次。

```java
public class Test {
    static volatile int i = 0;
    public static void main(String[] args) throws InterruptedException {
        for (int j=0; j<10; j++) {
            new Thread(() -> {
                i++;
            }).start();
            new Thread(() -> {
                i++;
            }).start();
        }

        System.out.println(i);
    }
}
```

**Atomic：读写操作要么同时发生，要么不发生**

```java
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    static AtomicInteger i = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        for (int j=0; j<1000; j++) {
            new Thread(() -> {
                i.getAndAdd(1);
            }).start();
            new Thread(() -> {
                i.getAndAdd(1);
            }).start();
        }

        Thread.sleep(1000);
        System.out.println(i);
    }
}
```

## 线程安全

应该叫`内存安全`，因为堆是共享内存，可以被所有线程访问
目前主流操作系统都是多任务的，即多个进程同时运行。
为了保证安全，每个进程只能访问分配给自己的内存空间。
`在每个进程的内存空间中都会有一块特殊的公共区域，堆`。
`进程内所有线程都可以访问到该区域`，这就是造成问题的潜在原因。

> 当多个线程访问一个对象时，如果不用进行额外的同步控制或者其他的协调操作，调用这个对象的行为都可以获得正确的结果，我们就说这个对象是线程安全的。

**堆**是进程和线程共有的空间，分为全局堆和局部堆。
全局堆就是所有没有分配的空间。
局部堆就是用户分配的空间。
堆在操作系统对进程初始化的时候分配，运行过程中也可以向系统要额外的堆，但是用完了要还给操作系统，不然就是内存泄漏。

> java中，堆是java虚拟机管理的内存中最大的一块，是`所有线程共享的一块内存区域`，在虚拟机启动时创建。堆所在的内存区域的唯一目的就是存放对象实例，几乎所有的对象实例以及数组都在这里分配内存。

**栈**是`每个线程独有的`。保存其运行状态 和局部自动变量。
栈在线程开始的时候初始化，每个线程的栈相互独立，因此栈是线程安全的。
操作系统在切换线程的时候会自动切换栈。