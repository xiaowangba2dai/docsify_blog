## java 内存区域

![image-20210406132931347](../images/java/image-20210406132931347.png)

`线程共享`

1. 堆：存放对象实例，几乎所有的对象都在这里分配空间

2. 方法区：静态变量、常量、类信息

   字符串常量池

`线程私有`

1. 程序计数器
2. 虚拟机栈
3. 本地方法栈

**（1）虚拟机栈**

虚拟机栈：每个线程运行时所需要的内存空间，线程私有

每个栈有多个栈帧（Frame）组成，对应着每次方法调用时所占用的内存。

程序的执行通过调用方法，每个方法定义一个栈帧，所以虚拟机栈会有一个或多个栈帧。

每个线程只能有一个活动栈帧，对应着当前正在执行的那个方法。

**栈帧**

组成：`局部变量表、操作数栈、动态连接、方法返回地址`

局部变量表：存放局部变量的列表

操作数栈：线程实际的操作台

动态链接：简单理解，指向运行时常量池的引用

在class文件里面，描述一个方法调用了其他方法，或者访问其成员变量是通过符号引用来表示的，动态链接的作用就是将这些符号引用所表示的方法转换为实际方法的直接引用。

方法返回地址：包括正常返回和异常返回，不同的返回类型有不同的指令

**栈内存溢出**

栈帧过多导致内存溢出

栈帧过大导致内存溢出

**（2）本地方法栈**

类似虚拟机栈，线程私有

本地方法栈服务的对象是JVM执行的native方法

而虚拟机栈服务的是JVM执行的java方法

**（3）堆**

堆是用于存放内存对象的内存区域。

`特点`：

线程共享，在虚拟机启动时创建。

堆的区域用来存放对象实例，因此也是垃圾收集器管理的主要区域。

堆在逻辑上划分为“新生代”和“老年代”

堆大小时可扩展的，使用"-xms"、“-xmx”控制最小和最大内存

**堆内存溢出**

内存真不够，通过调整堆内存大小解决

存在死循环，修改代码

**（4）方法区**

方法区是java虚拟机的一个模型规范，`存储每个类的结构`。

具体实现是`永久代和元空间`。

永久代是1.7的，1.8之后永久代就被移除了。

`元空间是分布在计算机内存`的，脱离了java虚拟机内存。

例如运行时常量池、字段和方法数据，以及方法和构造函数的代码，包括用于类和实例初始化以及接口初始化的特殊方法等。

![image-20210406144923270](../images/java/image-20210406144923270.png)

## 类生命周期

加载：查找并加载类的二进制数据（class文件）

 	1. 方法区：类的类信息
 	2. 堆：class文件对应的类实例

验证：确保加载的类信息是正确的

准备：为类的静态变量进行初始化，分配空间并赋予初始值

解析：将符号引用转换为直接引用

初始化：对类进行初始化，对静态变量赋予正确值

使用

卸载

## 类的加载过程

加载

验证

准备

解析

初始化

## 类加载器

启动类加载器（Bootstrap ClassLoader） （JDK/JRE/LIB）
负责加载JRE的核心类库，如JRE目录下的rt.jar, charsets.jar等

扩展类加载器（Extension ClassLoader）(JDK/JRE/LIB/EXT)
负责加载JRE扩展目录ext中jar类包

系统类加载器（Application ClassLoader）(自己定义的类，类路径下面)
负责加载classpath路径下的类包

## 类加载过程

（1）全盘负责委托机制

当一个ClassLoader加载一个类的时候，除非显示的使用另一个ClassLoader,，该类所依赖和引用的类也由这个ClassLoader载入

（2）双亲委派机制

先委托父类加载器寻找目标类，在找不到的情况下在自己的路径中查找并载入目标类

**双亲委派模式的优势**

沙箱安全机制：比如自己写的String.class类不会被加载，这样可以防止核心库被随意篡改

避免类的重复加载：当父ClassLoader已经加载了该类的时候，就不需要子ClassLoader再加载一次

## 如何用ClassLoader解决依赖冲突

