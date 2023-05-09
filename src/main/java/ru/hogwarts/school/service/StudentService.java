package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Object add(Student student) {
        logger.debug("Method add is Called");
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        logger.debug("Method getAll is Called");
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        logger.debug("Method getById is Called");
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    public Object update(Student student) {
        logger.debug("Method update is Called");
        return studentRepository.save(student);
    }

    public void delete(long id) {
        logger.debug("Method delete is Called");
        studentRepository.deleteById(id);
    }

    public List<Student> getByAge(int age) {
        logger.debug("Method getByAge is Called");
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
        logger.debug("Method findByAgeBetween is Called");
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
        logger.debug("Method findByFaculty is Called");
        return studentRepository.findByFaculty_Id(id);
    }

    public Student findByName(String name) {
        logger.debug("Method findByName is Called");
        return studentRepository.findByNameIgnoreCase(name);
    }

    public Faculty getFacultyByStudentName(String name) {
        logger.debug("Method getFacultyByStudentName is Called");
        Student student = studentRepository.findByNameIgnoreCase(name);
        if (student != null) {
            return student.getFaculty();
        } else {
            return null;
        }
    }

    public Long countOfStudents() {
        logger.debug("Method countOfStudents is Called");
        return studentRepository.count();
    }

    public Integer avgAgeOfStudents() {
        logger.debug("Method avgAgeOfStudents is Called");
        return studentRepository.avgAgeStudents();
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("Method getLastFiveStudents is Called");
        return studentRepository.findLastFiveStudents();
    }

    public List<String> sortNameByLetter(String letter) {
        logger.debug("Method sortNameByLetter is Called");
        List<Student> students = studentRepository.findAll();
        return students.parallelStream()
                .filter(s -> s.getName().startsWith(letter))
                .map(Student::getName)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageStudentAge() {
        logger.debug("Method getAverageStudentAge is Called");
        List<Student> students = studentRepository.findAll();
        return students.parallelStream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }
}
