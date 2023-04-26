package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty add(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public List getAll() {
        return facultyRepository.findAll();
    }

    public Object update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(long id) {
        facultyRepository.deleteById(id);
    }

    public Faculty getById(Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new RuntimeException("Faculty not found with id: " + id);
        }
    }

    public List<Faculty> getByColor(String color) {
        List<Faculty> faculties = facultyRepository.findAll(Sort.by("color").ascending());
        List<Faculty> result = new ArrayList<>();
        for (Faculty faculty : faculties) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        if (result.isEmpty()) {
            throw new RuntimeException("Color not found: " + color);
        }
        return result;
    }
}
