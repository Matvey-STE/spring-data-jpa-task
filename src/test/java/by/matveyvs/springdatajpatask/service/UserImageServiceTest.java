package by.matveyvs.springdatajpatask.service;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.dto.UserImageCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@IT
@RequiredArgsConstructor
class UserImageServiceTest {
    private final UserImageService userImageService;
    private static final String ORIGINAL_FILENAME = "Test original file name";
    private static final String UPDATED_ORIGINAL_FILENAME = "Updated original name";
    private static final Long USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
        UserImageCreateEditDto userImageCreateEditDto = new UserImageCreateEditDto(getMultipartFile(), USER_ID);
        var userImageReadDto = userImageService.create(userImageCreateEditDto);
        assertEquals(ORIGINAL_FILENAME,userImageReadDto.getImage());
        assertEquals(USER_ID,userImageReadDto.getUser().getId());
    }

    @Test
    void update() {
        UserImageCreateEditDto userImageCreateEditDto =
                new UserImageCreateEditDto(getMultipartFile(), USER_ID);
        var userImageReadDto = userImageService.create(userImageCreateEditDto);
        Optional<UserImageReadDto> update = userImageService.update(userImageReadDto.getId(),
                new UserImageCreateEditDto(getUpdatedMultipartFile(), UPDATED_USER_ID));

        assertTrue(update.isPresent());
        assertEquals(userImageReadDto.getId(), update.get().getId());
        assertEquals(UPDATED_USER_ID, update.get().getUser().getId());
    }

    @Test
    void deleteById() {
        UserImageCreateEditDto userImageCreateEditDto =
                new UserImageCreateEditDto(getMultipartFile(), USER_ID);
        var userImageReadDto = userImageService.create(userImageCreateEditDto);
        assertNotNull(userImageReadDto.getId());
        assertTrue(userImageService.deleteById(userImageReadDto.getId()));
        Optional<UserImageReadDto> byId = userImageService.findById(userImageReadDto.getId());
        assertFalse(byId.isPresent());
    }
    public MultipartFile getMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return ORIGINAL_FILENAME;
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

    public MultipartFile getUpdatedMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return UPDATED_ORIGINAL_FILENAME;
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