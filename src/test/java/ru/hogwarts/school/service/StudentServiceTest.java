package ru.hogwarts.school.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    @Test
    public void testAdd() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(20);
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        Student result = (Student) studentService.add(student);
        Assert.assertEquals(student, result);
    }

    @Test
    public void testGetAll() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("John Doe");
        student1.setAge(20);
        Student student2 = new Student();
        student2.setName("Jane Smith");
        student2.setAge(22);
        students.add(student1);
        students.add(student2);
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = studentService.getAll();
        Assert.assertEquals(students, result);
    }

    @Test
    public void testGetById() {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(20);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student result = studentService.getById(1L);
        Assert.assertEquals(student, result);
    }

    @Test
    public void testUpdate() {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(20);
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        Student result = (Student) studentService.update(student);
        Assert.assertEquals(student, result);
    }

    @Test
    public void testDelete() {
        studentService.delete(1L);
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetByAge() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("John Doe");
        student1.setAge(20);
        Student student2 = new Student();
        student2.setName("Jane Smith");
        student2.setAge(22);
        students.add(student1);
        students.add(student2);
        Mockito.when(studentRepository.findAll(Sort.by("age").ascending())).thenReturn(students);
        List<Student> result = studentService.getByAge(20);
        Assert.assertEquals(Collections.singletonList(student1), result);
    }

}
