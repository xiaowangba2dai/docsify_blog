package org.example;

import static org.junit.Assert.assertTrue;

import org.example.service.impl.SomeServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testDoSome() {
        SomeServiceImpl someService = new SomeServiceImpl();
        someService.doSome();
    }

    @Test
    public void testDoSomeSpring() {
        // 1. 指定spring配置文件的名称
        String config = "beans.xml";
        // 2. 创建表示spring容器的对象，ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext(config);
        SomeServiceImpl someService = context.getBean("someService", SomeServiceImpl.class);
        someService.doSome();
    }
}
