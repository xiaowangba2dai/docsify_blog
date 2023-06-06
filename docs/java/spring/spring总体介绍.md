

spring全家桶：spring、springmvc、spring boot、spring cloud

spring 学习官网：https://spring.io/projects/spring-framework

帮助开发人员创建对象，管理对象之间的关系。

核心：ioc、aop。能实现模块之间，类之间的`解耦合`。

## 框架怎么学

框架是一个其他人写好的软件。

1）知道框架能做什么？

2）框架语法，框架要完成一个功能，需要一定的步骤支持。

3）框架的内部实现，框架的内部怎么做。原理是什么。

4）通过学习，可以实现一个框架。

## spring 第一个核心功能：IoC

控制反转（Inversion of Control）是一种思想，通过容器来实现对象的装配和管理。

对象的创建、赋值、管理工作都交给容器来实现。

控制：创建对象、对象的属性赋值、对象之间的关系管理都能交给容器。

正转：开发人员在代码中，主动管理对象。

反转：把原来的开发人员管理、创建对象的权限转移给代码之外的容器来实现。

容器：是一个服务器软件，一个框架，比如spring。

```
public static void main(String[] args) {
	Student student = new Student(); // 自己创建对象 - 叫正转
}
```

**为什么要给容器管理对象呢？**

能减少对代码的改动，实现更多的功能。--> 解耦合

**IoC的技术实现：**

DI (Dependency Injection) 依赖注入，只需要在程序中提供要使用的对象名称就可以，至于对象如何在容器中创建，赋值，查找都由容器内部实现。

spring 使用DI 实现了IoC的功能，spring 底层创建对象，使用的是`反射机制`。