
package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {FacultyService.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class FacultyServiceTest {

    @MockBean
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyService facultyService;

    @Test
    public void addFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Engineering");
        faculty.setColor("Blue");

        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty addedFaculty = facultyService.add(faculty);

        assertEquals("Engineering", addedFaculty.getName());
        assertEquals("Blue", addedFaculty.getColor());
    }

    @Test
    public void getAllFacultiesTest() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(0L, "Engineering", "Blue"));
        faculties.add(new Faculty(1L, "Science", "Green"));

        when(facultyRepository.findAll()).thenReturn(faculties);

        List<Faculty> allFaculties = facultyService.getAll();

        assertEquals(2, allFaculties.size());
    }

    @Test
    public void updateFacultyTest() {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Engineering");
        faculty.setColor("Blue");

        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty updatedFaculty = (Faculty) facultyService.update(faculty);

        assertEquals("Engineering", updatedFaculty.getName());
        assertEquals("Blue", updatedFaculty.getColor());
    }

    @Test
    public void deleteFacultyTest() {
        long facultyId = 1L;

        facultyService.delete(facultyId);

        Mockito.verify(facultyRepository, times(1)).deleteById(facultyId);
    }

    @Test
    public void getFacultyByIdTest() {
        Long facultyId = 1L;
        Faculty faculty = new Faculty(0L, "Engineering", "Blue");
        faculty.setId(facultyId);

        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty));

        Faculty retrievedFaculty = facultyService.getById(facultyId);

        assertEquals(facultyId, retrievedFaculty.getId());
        assertEquals("Engineering", retrievedFaculty.getName());
        assertEquals("Blue", retrievedFaculty.getColor());
    }

    @Test
    public void getFacultyByNameOrColorTest() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(0L, "Engineering", "Blue"));
        faculties.add(new Faculty(1L, "Science", "Green"));
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("Engineering", "Blue")).thenReturn(faculties.subList(0, 1));
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("Green", "Red")).thenReturn(new ArrayList<>());

        List<Faculty> facultiesByNameOrColor = facultyService.getFacultyByNameOrColor("Engineering", "Blue");
        assertEquals(1, facultiesByNameOrColor.size());
        assertEquals("Engineering", facultiesByNameOrColor.get(0).getName());
        assertEquals("Blue", facultiesByNameOrColor.get(0).getColor());

        List<Faculty> facultiesByNameOrColor2 = facultyService.getFacultyByNameOrColor("Green", "Red");
        assertEquals(0, facultiesByNameOrColor2.size());
    }
}
