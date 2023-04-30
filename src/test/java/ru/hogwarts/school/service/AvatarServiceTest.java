package ru.hogwarts.school.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;



@ContextConfiguration(classes = {AvatarService.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class AvatarServiceTest {
    @Autowired
    private AvatarService avatarService;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void uploadAvatar() throws Exception {
        Student student = new Student();
        student.setName("John");
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test data".getBytes());
        Avatar savedAvatar = new Avatar();
        savedAvatar.setStudent(student);
        Mockito.when(avatarRepository.save(Mockito.any(Avatar.class))).thenReturn(savedAvatar);
        avatarService.uploadAvatar(student.getId(), file);
        Mockito.verify(avatarRepository, Mockito.times(1)).save(Mockito.any(Avatar.class));
    }
}