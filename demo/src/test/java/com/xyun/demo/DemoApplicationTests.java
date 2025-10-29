package com.xyun.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyun.demo.util.Student;
import com.xyun.demo.service.StudentService;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前，清理数据库并准备测试数据
        studentService.deleteAll();
        
        testStudent = new Student();
        testStudent.setName("张三");
        testStudent.setAge(20);
        testStudent.setGrade("大二");
    }

    @Test
    void contextLoads() {
        // 测试Spring上下文是否正确加载
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        String studentJson = objectMapper.writeValueAsString(testStudent);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testStudent.getName()))
                .andExpect(jsonPath("$.age").value(testStudent.getAge()))
                .andExpect(jsonPath("$.grade").value(testStudent.getGrade()));
    }

    @Test
    void getAllStudents_WhenNoStudents_ShouldReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllStudents_WhenStudentsExist_ShouldReturnStudentList() throws Exception {
        // 先创建一个学生
        Student savedStudent = studentService.save(testStudent);

        mockMvc.perform(get("/api/students"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedStudent.getId()))
                .andExpect(jsonPath("$[0].name").value(savedStudent.getName()))
                .andExpect(jsonPath("$[0].age").value(savedStudent.getAge()))
                .andExpect(jsonPath("$[0].grade").value(savedStudent.getGrade()));
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() throws Exception {
        // 先创建一个学生
        Student savedStudent = studentService.save(testStudent);

        mockMvc.perform(get("/api/students/{id}", savedStudent.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value(savedStudent.getName()));
    }

    @Test
    void getStudentById_WhenStudentDoesNotExist_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/students/{id}", 999L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldUpdateAndReturnStudent() throws Exception {
        // 先创建一个学生
        Student savedStudent = studentService.save(testStudent);
        
        // 修改学生信息
        savedStudent.setName("李四");
        savedStudent.setAge(21);
        
        String updatedStudentJson = objectMapper.writeValueAsString(savedStudent);

        mockMvc.perform(put("/api/students/{id}", savedStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedStudentJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedStudent.getId()))
                .andExpect(jsonPath("$.name").value("李四"))
                .andExpect(jsonPath("$.age").value(21));
    }

    @Test
    void updateStudent_WhenStudentDoesNotExist_ShouldReturn404() throws Exception {
        testStudent.setId(999L);
        String studentJson = objectMapper.writeValueAsString(testStudent);

        mockMvc.perform(put("/api/students/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldDeleteSuccessfully() throws Exception {
        // 先创建一个学生
        Student savedStudent = studentService.save(testStudent);

        mockMvc.perform(delete("/api/students/{id}", savedStudent.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        // 验证学生已被删除
        mockMvc.perform(get("/api/students/{id}", savedStudent.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_WhenStudentDoesNotExist_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/api/students/{id}", 999L))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }
}
