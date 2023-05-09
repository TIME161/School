package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty add(Faculty faculty) {
        logger.debug("Method add is Called");
        return facultyRepository.save(faculty);
    }

    public List<Faculty> getAll() {
        logger.debug("Method getAll is Called");
        return facultyRepository.findAll();
    }

    public Object update(Faculty faculty) {
        logger.debug("Method update is Called");
        return facultyRepository.save(faculty);
    }

    public void delete(long id) {
        logger.debug("Method delete is Called");
        facultyRepository.deleteById(id);
    }

    public Faculty getById(Long id) {
        logger.debug("Method getById is Called");
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get();
        } else {
            throw new RuntimeException("Faculty not found with id: " + id);
        }
    }

    public List<Faculty> getFacultyByNameOrColor(String name, String color) {
        logger.debug("Method getFacultyByNameOrColor is Called");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Faculty getFacultyWithLongestName() {
        logger.debug("Method getFacultyWithLongestName is Called");
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.parallelStream()
                .max(Comparator.comparing(f -> f.getName().length()))
                .orElse(null);
    }
}

