package com;

import com.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyTest {

    @Test
    public void testSelectStudentByAge() throws IOException {
        // 调用mybatis某个对象的方法，调用sql语句
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Student student = (Student) session.selectOne("com.dao.StudentDao.selectStudentByAge");
            System.out.println(student);
        }
    }
}
