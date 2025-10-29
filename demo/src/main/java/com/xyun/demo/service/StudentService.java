package com.xyun.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xyun.demo.util.Student;

@Repository
public interface StudentService extends JpaRepository<Student, Long> {
}