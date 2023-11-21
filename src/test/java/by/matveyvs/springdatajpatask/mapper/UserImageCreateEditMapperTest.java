package by.matveyvs.springdatajpatask.mapper;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.dto.UserImageCreateEditDto;
import by.matveyvs.springdatajpatask.entity.UserImage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserImageCreateEditMapperTest {
    private final UserImageCreateEditMapper userImageCreateEditMapper;
    private final static Long USER_ID = 1L;
    private final static String ORIGINAL_FILE_NAME = "Original file name";


    @Test
    void map() {
        var userImageCreateDto =
                new UserImageCreateEditDto(getMultipartFile(), USER_ID);
        UserImage map = userImageCreateEditMapper.map(userImageCreateDto);

        assertEquals(userImageCreateDto.getImage().getOriginalFilename(), map.getImage());
        assertEquals(USER_ID, map.getUser().getId());
    }

    public MultipartFile getMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return ORIGINAL_FILE_NAME;
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