package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyService {
    private long counterId = 0;

    private final Map<Long, Faculty> faculties = new HashMap<>();

    public Faculty add(String name, String color) {
        long id = counterId;
        counterId++;
        Faculty newFaculty = new Faculty(id, name, color);
        faculties.put(id, newFaculty);
        return newFaculty;
    }

    public Map<Long, Faculty> getAll() {
        return faculties;
    }

    public Faculty update(long id, String name, String color) {
        Faculty facultyForUpdate = faculties.get(id);
        facultyForUpdate.setName(name);
        facultyForUpdate.setColor(color);
        return facultyForUpdate;
    }

    public Faculty delete(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> findByColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (faculty.getColor() != null && faculty.getColor().equals(color)) {
                result.add(faculty);
            }
        }
        return result;
    }
}
