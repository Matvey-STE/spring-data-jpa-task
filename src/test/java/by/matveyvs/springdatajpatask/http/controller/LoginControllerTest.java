package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserReadDto;
import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static by.matveyvs.springdatajpatask.dto.UserCreateEditDto.Fields.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
@RequiredArgsConstructor
class LoginControllerTest {
    private final UserService userService;
    private final MockMvc mockMvc;
    private UserReadDto userReadDtoUser;
    private UserReadDto userReadDtoAdmin;

    @BeforeEach
    void setUp() {
        userReadDtoUser = userService.create(getUserCreateUpdateDtoUser());
        userReadDtoAdmin = userService.create(getUserCreateUpdateDtoAdmin());
    }

    @AfterEach
    void tearDown() {
        userService.deleteById(userReadDtoUser.getId());
        userService.deleteById(userReadDtoAdmin.getId());
    }

    private UserCreateEditDto getUserCreateUpdateDtoUser() {
        return new UserCreateEditDto(
                "testUserLogin@mail.com",
                "123",
                LocalDate.now(),
                "firstName",
                "lastName",
                Role.USER,
                null,
                getMultipartFile()
        );
    }

    private UserCreateEditDto getUserCreateUpdateDtoAdmin() {
        return new UserCreateEditDto(
                "testAdminLogin@mail.com",
                "123",
                LocalDate.now(),
                "firstName",
                "lastName",
                Role.ADMIN,
                null,
                getMultipartFile()
        );
    }

    @Test
    void loginAsUser() throws Exception {
        mockMvc.perform(post("/login")
                        .param(username, userReadDtoUser.getUsername())
                        .param(password, userReadDtoUser.getPassword())
                        .param(image, userReadDtoUser.getImage())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + userReadDtoUser.getId())
                );
    }

    @Test
    void loginAsAdmin() throws Exception {
        mockMvc.perform(post("/login")
                        .param(username, userReadDtoAdmin.getUsername())
                        .param(password, userReadDtoAdmin.getPassword())
                        .param(image, userReadDtoAdmin.getImage())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }

    public MultipartFile getMultipartFile() {
        Path path = Paths.get("/Users/matvey/MyProjects/spring-data-jpa-task/src/test/resources/testObject/bg-1.jpeg");
        String name = "bg-1.jpeg";
        String originalFileName = "bg-1.jpeg";
        String contentType = "jpeg/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MockMultipartFile(name,
                originalFileName, contentType, content);
    }
}