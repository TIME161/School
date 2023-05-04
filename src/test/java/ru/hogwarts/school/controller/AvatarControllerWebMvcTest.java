package ru.hogwarts.school.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AvatarController.class)
public class AvatarControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private AvatarController avatarController;

    @Test
    public void testGetAllAvatars() throws Exception {

        List<Avatar> avatars = new ArrayList<>();
        Avatar avatar1 = new Avatar(1L, "path/to/avatar1", 1000L, "image/jpeg", new byte[]{110});
        Avatar avatar2 = new Avatar(2L, "path/to/avatar2", 2000L, "image/png", new byte[]{0101});
        avatars.add(avatar1);
        avatars.add(avatar2);
        when(avatarService.findAllAvatars()).thenReturn(avatars);


        mockMvc.perform(MockMvcRequestBuilders.get("/avatars"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filePath").value("path/to/avatar1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fileSize").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mediaType").value("image/jpeg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].data").value("bg=="))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].filePath").value("path/to/avatar2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fileSize").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mediaType").value("image/png"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data").value("QQ=="));
    }

    @Test
    public void testFindAllAvatarsWithPages() throws Exception {
        List<Avatar> avatars = new ArrayList<>();
        Avatar avatar1 = new Avatar(1L, "path/to/avatar1", 1000L, "image/jpeg", new byte[]{110});
        Avatar avatar2 = new Avatar(2L, "path/to/avatar2", 2000L, "image/png", new byte[]{0101});
        avatars.add(avatar1);
        avatars.add(avatar2);

        Page<Avatar> page = new PageImpl<>(avatars);

        when(avatarRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Integer pageNumber = 1;
        Integer pageSize = 10;

        mockMvc.perform(MockMvcRequestBuilders.get("/avatars")
                        .param("pageNumber", pageNumber.toString())
                        .param("pageSize", pageSize.toString()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].filePath").value("path/to/avatar1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fileSize").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mediaType").value("image/jpeg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].data").value("bg=="))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].filePath").value("path/to/avatar2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fileSize").value(2000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mediaType").value("image/png"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].data").value("QQ=="));
    }
}

