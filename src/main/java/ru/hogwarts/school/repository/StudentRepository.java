package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFaculty_Id(Long id);
    Student findByNameIgnoreCase(String name);

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFiveStudents();

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Integer howMuchStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer avgAgeStudents();
}
