package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public Student add(@RequestBody Student request) {
        return studentService.add(request.getName(), request.getAge());
    }

    @GetMapping("/getAll")
    public Map<Long, Student> getAll() {
        return studentService.getAll();
    }

    @PutMapping
    public Student update(@RequestBody Student request) {
        return studentService.update(request.getId(), request.getName(), request.getAge());
    }

    @DeleteMapping
    public Student delete(long id) {
        return studentService.delete(id);
    }

    @GetMapping("{age}")
    public Collection<Student> findByAge(@PathVariable int age) {
        return studentService.findByAge(age);
    }
}
