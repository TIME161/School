package ru.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;
    @InjectMocks
    private StudentController studentController;

    @Test
    public void testAdd() throws Exception {
        final long id = 1;
        final String name = "Harold";
        final int age = 16;
        Faculty faculty = new Faculty(1L, "Puff", "Red");

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", faculty);

        Student student = new Student(id, name, age, faculty);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));

    }

    @Test
    public void testUpdate() throws Exception {
        final long id = 1;
        final String name = "Harold";
        final int age = 16;
        Faculty faculty = new Faculty(1L, "Puff", "Red");
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", faculty);

        Student updatedStudent = new Student(id, name, age, faculty);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(updatedStudent));

        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void testDelete() throws Exception {
        final long id = 1;
        final String name = "Harold";
        final int age = 16;
        Faculty faculty = new Faculty(1L, "Puff", "Red");
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", faculty);
        Student student = new Student(id, name, age, faculty);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.faculty").value(faculty));


        mockMvc.perform(MockMvcRequestBuilders.delete("/student?id=" + id))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void testGetAll() throws Exception {
        final long id1 = 1;
        final String name1 = "Harold";
        final int age1 = 16;
        Faculty faculty1 = new Faculty(1L, "Puff", "Red");
        Student student1 = new Student(id1, name1, age1, faculty1);

        final long id2 = 2;
        final String name2 = "Julia";
        final int age2 = 18;
        Faculty faculty2 = new Faculty(2L, "Puff", "Blue");
        Student student2 = new Student(id2, name2, age2, faculty2);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1))
                .andExpect(jsonPath("$[0].faculty").value(faculty1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2))
                .andExpect(jsonPath("$[1].faculty").value(faculty2));
    }

    @Test
    public void testGetCountStudents() throws Exception {

        int count = 10;
        when(studentService.countOfStudents()).thenReturn(count);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/info/count"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    public void testAvgAgeOfStudents() throws Exception {
        int avgAge = 20;
        when(studentService.avgAgeOfStudents()).thenReturn(avgAge);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/info/avg-age"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(avgAge)));
    }

    @Test
    public void testLastFiveStudents() throws Exception {
        Faculty faculty = new Faculty(1L, "Puff", "Red");
        List<Student> lastFiveStudents = Arrays.asList(
                new Student(1L, "John", 15, faculty),
                new Student(2L, "Jane", 15, faculty),
                new Student(3L, "Bob", 15, faculty),
                new Student(4L, "Alice", 15, faculty),
                new Student(5L, "David", 15, faculty)
        );
        when(studentService.getLastFiveStudents()).thenReturn(lastFiveStudents);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/info/last-five-students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].age").value(15))
                .andExpect(jsonPath("$[0].faculty").value(faculty))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[1].age").value(15))
                .andExpect(jsonPath("$[1].faculty").value(faculty))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Bob"))
                .andExpect(jsonPath("$[2].age").value(15))
                .andExpect(jsonPath("$[2].faculty").value(faculty))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].name").value("Alice"))
                .andExpect(jsonPath("$[3].age").value(15))
                .andExpect(jsonPath("$[3].faculty").value(faculty))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].name").value("David"))
                .andExpect(jsonPath("$[4].age").value(15))
                .andExpect(jsonPath("$[4].faculty").value(faculty));

    }
}

