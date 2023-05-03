package ru.hogwarts.school.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Object add(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public Object update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getByAge(int age) {
        List<Student> students = studentRepository.findAll(Sort.by("age").ascending());
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        if (result.isEmpty()) {
            throw new RuntimeException("Student not found with age: " + age);
        }
        return result;
    }

    public List<Student> findByAgeBetween(int min, int max) {
        List<Student> students = studentRepository.findAll(Sort.by("age").ascending());
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            int age = student.getAge();
            if (age >= min && age <= max) {
                result.add(student);
            }
        }
        if (result.isEmpty()) {
            throw new RuntimeException("Students not found with age in range: " + min + " - " + max);
        }
        return result;
    }

    public List<Student> findByFaculty(Long id) {
        return studentRepository.findByFaculty_Id(id);
    }

    public Student findByName(String name) {
        return studentRepository.findByNameIgnoreCase(name);
    }

    public Faculty getFacultyByStudentName(String name) {
        Student student = studentRepository.findByNameIgnoreCase(name);
        if (student != null) {
            return student.getFaculty();
        } else {
            return null;
        }
    }

    public Integer countOfStudents() {
        return studentRepository.howMuchStudents();
    }

    public Integer avgAgeOfStudents() {
        return studentRepository.avgAgeStudents();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.findLastFiveStudents();
    }
}
