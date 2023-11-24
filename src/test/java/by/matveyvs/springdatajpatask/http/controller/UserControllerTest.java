package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.config.IT;
import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static by.matveyvs.springdatajpatask.dto.UserCreateEditDto.Fields.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest {
    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", IsCollectionWithSize.hasSize(5)));
    }

    @Test
    void findById() throws Exception {
        Long userId = 1L;
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/user"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user",
                        hasProperty("username", is("ivan@gmail.com"))));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "test@mail.ru")
                        .param(password, "123")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(role, "ADMIN")
                        .param(companyId, "1")
                        .param(birthDate, "01-01-2000")
                        .param(image, "test")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        Long userId = 1L;
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test-image.jpg",
                "image/jpeg",
                "some image data".getBytes()
        );
        mockMvc.perform(multipart("/users/{userId}/update", userId)
                        .file(file)
                        .param(username, "test@mail.ru")
                        .param(password, "123")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(role, "USER")
                        .param(companyId, "2")
                        .param(birthDate, "02-01-2000")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + userId)
                );
    }

    @Test
    void addUserImage() throws Exception {
        Long userId = 1L;
        MockMultipartFile file = new MockMultipartFile(
                "image",           // This should match the parameter name in your controller method
                "test-image.jpg",  // File name
                "image/jpeg",      // Content type
                "some image data".getBytes()  // File content
        );
        mockMvc.perform(multipart("/users/{userId}/userImages/addImage", userId)
                .file(file)
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrlPattern("/users/{\\d+}/userImages")
        );
    }

    @Test
    void delete() throws Exception {
        Long failUserId = -1L;

        mockMvc.perform(post("/users/{id}/delete", failUserId))
                .andExpectAll(
                        status().is4xxClientError()
                );

        Long successUserId = 1L;
        mockMvc.perform(post("/users/{id}/delete", successUserId))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }
}