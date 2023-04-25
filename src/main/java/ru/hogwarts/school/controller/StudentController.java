package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @PostMapping
    public Student add(@RequestBody Student request) {

        return (Student) studentService.add(request);
    }

    @PutMapping
    public Object update(@RequestBody Student request) {

        return studentService.update(request);
    }

    @DeleteMapping()
    public ResponseEntity delete(@RequestParam Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<Student>> getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer age) {

        List<Student> students;

        if (id != null) {
            Student student = studentService.getById(id);
            students = Collections.singletonList(student);
        } else if (age != null) {
            students = studentService.getByAge(age);
        } else {
            students = studentService.getAll();
        }

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }
}
