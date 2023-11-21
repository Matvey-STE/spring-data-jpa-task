package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.config.IT;
import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static by.matveyvs.springdatajpatask.dto.UserCreateEditDto.Fields.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        Long userId = 1L;
        mockMvc.perform(post("/users/{id}/update", userId)
                        .param("username", "test@mail.ru")
                        .param("password", "123")
                        .param("birthDate", "1984-03-14"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
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