package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Object add(@RequestBody Faculty request) {
        return facultyService.add(request);
    }

    @PutMapping
    public Object update(@RequestBody Faculty request) {
        return facultyService.update(request);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<Faculty>> getFaculties(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color) {

        List<Faculty> faculties;

        if (id != null) {
            Faculty faculty = facultyService.getById(id);
            faculties = Collections.singletonList(faculty);
        }  else if (name != null || color != null) {
            faculties = facultyService.getFacultyByNameOrColor(name,color);
        }
        else {
            faculties = facultyService.getAll();
        }

        if (faculties.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(faculties);
        }
    }
}