package com.xyun.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xyun.demo.service.StudentService;
import com.xyun.demo.util.Student;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (!studentService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        student.setId(id);
        return ResponseEntity.ok(studentService.save(student));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}