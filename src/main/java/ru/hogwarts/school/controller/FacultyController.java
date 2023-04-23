package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty request) {
        return facultyService.add(request.getName(), request.getColor());
    }

    @GetMapping("/getAll")
    public Map<Long, Faculty> getAll() {
        return facultyService.getAll();
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty request) {
        return facultyService.update(request.getId(), request.getName(), request.getColor());
    }

    @DeleteMapping
    public Faculty delete(long id) {
        return facultyService.delete(id);
    }

    @GetMapping("{color}")
    public Collection<Faculty> findByColor(@PathVariable String color) {
        return facultyService.findByColor(color);
    }
}