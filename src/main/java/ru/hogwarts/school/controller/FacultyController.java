package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

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

    @GetMapping("/getAll")
    public List getAll() {
        return facultyService.getAll();
    }

    @PutMapping
    public Object update(@RequestBody Faculty request) {
        return facultyService.update(request);
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public List<Faculty> getByAge(@PathVariable String color) {
        return facultyService.getByColor(color);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Faculty> getStudentById(@PathVariable Long id) {
        Faculty faculty = facultyService.getById(id);
        return ResponseEntity.ok(faculty);
    }
}