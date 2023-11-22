package by.matveyvs.springdatajpatask.service;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import by.matveyvs.springdatajpatask.dto.UserReadDto;
import by.matveyvs.springdatajpatask.entity.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserServiceTest {
    private final static Long USER_ID = 1L;
    private static final Integer COMPANY_ID = 1;

    private final UserService userService;
    private static final String STRING_TEST = "bg-1.jpeg";

    @Test
    void findAll() {
        List<UserReadDto> all = userService.findAll();
        assertEquals(5, all.size());
    }

    @Test
    void findById() {
        Optional<UserReadDto> result = userService.findById(USER_ID);
        assertTrue(result.isPresent());
        result.ifPresent(userDto -> assertEquals("ivan@gmail.com", userDto.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@test.com",
                "password",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_ID,
                getMultipartFile()
        );
        UserReadDto actualResult = userService.create(userDto);
        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getPassword(), actualResult.getPassword());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getCompanyId(), actualResult.getCompanyReadDto().getId());
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@test.com",
                "password",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_ID,
                getMultipartFile()
        );
        Optional<UserReadDto> actualResult = userService.update(USER_ID, userDto);
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> {
            assertEquals(userDto.getUsername(), actual.getUsername());
            assertEquals(userDto.getPassword(), actual.getPassword());
            assertEquals(userDto.getBirthDate(), actual.getBirthDate());
            assertEquals(userDto.getFirstname(), actual.getFirstname());
            assertEquals(userDto.getLastname(), actual.getLastname());
            assertEquals(userDto.getCompanyId(), actual.getCompanyReadDto().getId());
            assertSame(userDto.getRole(), actual.getRole());
            assertEquals(STRING_TEST, actual.getImage());
        });
    }

    @Test
    void deleteById() {
        assertFalse(userService.deleteById(-22222L));
        assertTrue(userService.deleteById(USER_ID));
    }

    @Test
    void addImageToList() {
        var imageId = userService.addUserImage(USER_ID, getMultipartFile());
        assertNotNull(imageId);
        Optional<UserReadDto> userReadDto = userService.findById(USER_ID);
        var userImageList = userReadDto.get().getUserImageReadDtoList();
        Optional<UserImageReadDto> first = userImageList.stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst();
        assertEquals(getMultipartFile().getOriginalFilename(), first.get().getImage());
        for (UserImageReadDto userImageReadDto : userImageList) {
            System.out.println("Image is " + userImageReadDto.getImage());
        }
        assertFalse(userImageList.isEmpty());
    }

    @Test
    void removeImageFromList() {
        var userImageId = userService.addUserImage(USER_ID, getMultipartFile());
        Optional<UserReadDto> userReadDto = userService.findById(USER_ID);
        assertTrue(userReadDto.isPresent());
        System.out.println(userImageId);
        userService.removeUserImage(USER_ID, userImageId)
                .ifPresent(user -> {
                    List<UserImageReadDto> list = user.getUserImageReadDtoList().stream()
                            .filter(image -> image.getId().equals(userImageId)).toList();
                    assertTrue(list.isEmpty());
                });

    }

    public MultipartFile getMultipartFile() {
        Path path = Paths.get("/Users/matvey/MyProjects/spring-data-jpa-task/src/test/resources/testObject/bg-1.jpeg");
        String name = "bg-1.jpeg";
        String originalFileName = STRING_TEST;
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