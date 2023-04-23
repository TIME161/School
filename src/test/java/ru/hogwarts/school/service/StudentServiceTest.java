package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import java.util.Collection;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    @Test
    void add() {
        Student newStudent = studentService.add("John", 20);
        assertNotNull(newStudent);
        assertEquals("John", newStudent.getName());
        assertEquals(20, newStudent.getAge());
    }

    @Test
    void getAll() {
        studentService.add("John", 20);
        studentService.add("Alice", 21);

        Map<Long, Student> students = studentService.getAll();
        assertNotNull(students);
        assertEquals(2, students.size());
    }

    @Test
    void update() {
        Student newStudent = studentService.add("John", 20);
        Student updatedStudent = studentService.update(newStudent.getId(), "John Doe", 21);
        assertNotNull(updatedStudent);
        assertEquals("John Doe", updatedStudent.getName());
        assertEquals(21, updatedStudent.getAge());
    }

    @Test
    void delete() {
        Student newStudent = studentService.add("John", 20);
        Student deletedStudent = studentService.delete(newStudent.getId());
        assertNotNull(deletedStudent);
        assertEquals("John", deletedStudent.getName());
        assertEquals(20, deletedStudent.getAge());
        assertTrue(studentService.getAll().isEmpty());
    }

    @Test
    void findByAge() {
        studentService.add("John", 20);
        studentService.add("Alice", 21);
        studentService.add("Bob", 20);

        Collection<Student> students = studentService.findByAge(20);
        assertNotNull(students);
        assertEquals(2, students.size());
    }
}