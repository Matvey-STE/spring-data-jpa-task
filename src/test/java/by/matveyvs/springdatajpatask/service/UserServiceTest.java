package by.matveyvs.springdatajpatask.service;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserReadDto;
import by.matveyvs.springdatajpatask.entity.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private static final String STRING_TEST = "TEST STRING";

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

    public MultipartFile getMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return STRING_TEST;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }
}