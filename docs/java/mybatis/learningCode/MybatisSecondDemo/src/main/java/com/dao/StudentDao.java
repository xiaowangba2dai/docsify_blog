package com.dao;

import com.domain.Student;

public interface StudentDao {
    Student getStudentByName(String name);
}
