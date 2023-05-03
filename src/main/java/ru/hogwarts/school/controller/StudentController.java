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
    public Object getStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nameForFindFaculty,
            @RequestParam(required = false) Long facultyId,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {

        List<Student> students;

        if (id != null) {
            Student student = studentService.getById(id);
            students = Collections.singletonList(student);
        } else if (name != null) {
            Student student = studentService.findByName(name);
            students = Collections.singletonList(student);
        }
        else if (nameForFindFaculty != null) {
            return studentService.getFacultyByStudentName(nameForFindFaculty);
        }
        else if (facultyId != null) {
            students = studentService.findByFaculty(facultyId);
        }
        else if (age != null) {
            students = studentService.getByAge(age);
        } else if (min != null && max !=null) {
            students = studentService.findByAgeBetween(min, max);
        } else {
            students = studentService.getAll();
        }

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }

    @GetMapping("/info/count")
    public Integer getCountStudents() {
        return studentService.countOfStudents();
    }

    @GetMapping("/info/avg-age")
    public Integer avgAgeOfStudents() {
        return studentService.avgAgeOfStudents();
    }

    @GetMapping("/info/last-five-students")
    public List<Student> lastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

}
