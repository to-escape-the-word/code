# Spring Boot 基础教程项目

本教程将指导你如何使用 Spring Boot 构建一个基础的 Web 应用程序。

## 项目概述

- 项目名称：Spring Boot Demo
- Java 版本：17
- Spring Boot 版本：3.5.7
- 构建工具：Maven

## 项目结构

```bash
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── xyun/
│   │   │           └── demo/
│   │   │               ├── DemoApplication.java
│   │   │               ├── Student.java
│   │   │               └── StudentController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
└── pom.xml
```

## 核心功能

1. RESTful API 实现
2. 数据库集成（JPA）
3. 基本 CRUD 操作
4. 统一异常处理
5. API 文档生成

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.8+
- IDE（推荐使用 IntelliJ IDEA 或 VS Code）

### 2. 构建运行

```bash
# 克隆项目
git clone <项目地址>

# 进入项目目录
cd demo

# 编译项目
./mvnw clean package

# 运行项目
./mvnw spring-boot:run
```

### 3. 访问测试

应用启动后，可以通过以下地址访问：

- 主页：[http://localhost:8080](http://localhost:8080)
- RESTful API：[http://localhost:8080/api/students](http://localhost:8080/api/students)

## API 文档

### Student API

#### 获取所有学生

- 请求方法：GET
- URL：`/api/students`
- 响应示例：

```json
[
    {
        "id": 1,
        "name": "张三",
        "age": 20,
        "grade": "大二"
    }
]
```

#### 创建新学生

- 请求方法：POST
- URL：`/api/students`
- 请求体示例：

```json
{
    "name": "李四",
    "age": 19,
    "grade": "大一"
}
```

## 代码示例

### 实体类 (Student.java)

```java
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Integer age;
    private String grade;
}
```

### 控制器 (StudentController.java)

```java
@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }
    
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }
}
```

## 配置说明

### application.properties

```properties
# 服务器配置
server.port=8080

# 数据库配置
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA配置
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# 日志级别
logging.level.root=INFO
logging.level.com.xyun.demo=DEBUG
```

## 高级特性

1. 统一异常处理
2. 请求参数验证
3. 跨域配置
4. 安全认证

## 常见问题

### Q1: 如何修改服务器端口？

A1: 在 `application.properties` 中修改 `server.port` 属性。

### Q2: 如何切换数据库？

A2: 修改 `application.properties` 中的数据源配置，并添加相应的数据库驱动依赖。

## 下一步学习

1. Spring Security 集成
2. Redis 缓存
3. 消息队列
4. 微服务架构

## 参考资源

- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring 官方教程](https://spring.io/guides)
- [Maven 文档](https://maven.apache.org/guides/)

## 核心功能

1. RESTful API 实现
2. 数据库集成（JPA）
3. 基本 CRUD 操作
4. 统一异常处理
5. API 文档生成

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.8+
- IDE（推荐使用 IntelliJ IDEA 或 VS Code）

### 2. 构建运行

```bash
# 克隆项目
git clone <项目地址>

# 进入项目目录
cd demo

# 编译项目
./mvnw clean package

# 运行项目
./mvnw spring-boot:run
```

### 3. 访问测试

应用启动后，可以通过以下地址访问：

- 主页：http://localhost:8080
- RESTful API：http://localhost:8080/api/students

## API 文档

### Student API

#### 获取所有学生
- 请求方法：GET
- URL：`/api/students`
- 响应示例：
```json
[
    {
        "id": 1,
        "name": "张三",
        "age": 20,
        "grade": "大二"
    }
]
```

#### 创建新学生
- 请求方法：POST
- URL：`/api/students`
- 请求体示例：
```json
{
    "name": "李四",
    "age": 19,
    "grade": "大一"
}
```

## 代码示例

### 实体类 (Student.java)

```java
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Integer age;
    private String grade;
}
```

### 控制器 (StudentController.java)

```java
@RestController
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }
    
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.save(student);
    }
}
```

## 配置说明

### application.properties

```properties
# 服务器配置
server.port=8080

# 数据库配置
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA配置
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# 日志级别
logging.level.root=INFO
logging.level.com.xyun.demo=DEBUG
```

## 高级特性

1. 统一异常处理
2. 请求参数验证
3. 跨域配置
4. 安全认证

## 常见问题

### Q1: 如何修改服务器端口？
A1: 在 `application.properties` 中修改 `server.port` 属性。

### Q2: 如何切换数据库？
A2: 修改 `application.properties` 中的数据源配置，并添加相应的数据库驱动依赖。

## 下一步学习

1. Spring Security 集成
2. Redis 缓存
3. 消息队列
4. 微服务架构

## 参考资源

- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring 官方教程](https://spring.io/guides)
- [Maven 文档](https://maven.apache.org/guides/)