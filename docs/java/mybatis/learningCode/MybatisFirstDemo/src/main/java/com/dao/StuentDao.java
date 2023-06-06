package com.dao;

import com.domain.Student;

public interface StuentDao {
    // 查询一个学生
    Student selectStudentByAge(int age);
}
