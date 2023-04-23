package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {
    private  final Map<Long, Student> students = new HashMap<>();
    private long counterId = 0;


    public Student add(String name, int age) {
        long id = counterId;
        counterId++;
        Student newStudent = new Student(id, name, age);
        students.put(id, newStudent);
        return newStudent;
    }

    public Map<Long, Student> getAll() {
        return students;
    }

    public Student update(long id, String name, int age) {
        Student studentForUpdate = students.get(id);
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return studentForUpdate;
    }

    public Student delete(long id) {
        return students.remove(id);
    }

    public Collection<Student> findByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }
}
