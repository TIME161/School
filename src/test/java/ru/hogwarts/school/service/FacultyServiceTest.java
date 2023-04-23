package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {FacultyService.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FacultyServiceTest {
    private final FacultyService facultyService;

    @Autowired
    FacultyServiceTest(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @Test
    void add() {
        String expectedResult = "Facility{id=0, name='Slytherin', color='green'}";

        String actualResult = facultyService.add("Slytherin","green").toString();
        assertEquals(actualResult,expectedResult);
    }

    @Test
    void getAll() {
        facultyService.add("Slytherin","green");
        facultyService.add("Slytherin1","green");
        String expectedResult = "{0=Facility{id=0, name='Slytherin', color='green'}, 1=Facility{id=1, name='Slytherin1', color='green'}}";
        String actualResult = facultyService.getAll().toString();
        assertEquals(actualResult,expectedResult);
    }

    @Test
    void update() {
        facultyService.add("Slytherin","green");
        facultyService.add("Slytherin1","green");
        String actualResult = facultyService.update(1,"Slytherin2","green").toString();
        String expectedResult = "Facility{id=1, name='Slytherin2', color='green'}";
        assertEquals(actualResult,expectedResult);
    }

    @Test
    void delete() {
        facultyService.add("Slytherin","green");
        facultyService.add("Slytherin1","green");

    }

    @Test
    void findByColor() {
        facultyService.add("Slytherin","green");
        facultyService.add("Slytherin1","black");

        String expectedResult = "[Facility{id=0, name='Slytherin', color='green'}]";

        String actualResult = facultyService.findByColor("green").toString();
        assertEquals(actualResult,expectedResult);
    }
}