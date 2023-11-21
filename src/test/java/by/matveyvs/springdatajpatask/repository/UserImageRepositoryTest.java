package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.entity.UserImage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class UserImageRepositoryTest {
    private final UserImageRepository userImageRepository;
    private final UserRepository userRepository;
    private final static Long USER_ID = 1L;
    private final static String USER_IMAGE_TEST = "Test image path";
    private final static String USER_IMAGE_UPDATED = "Test image path UPDATED";

    @Test
    void create() {
        UserImage userImage = UserImage.builder()
                .image(USER_IMAGE_TEST)
                .user(userRepository.findById(USER_ID).orElse(null))
                .build();
        UserImage savedUserImage = userImageRepository.saveAndFlush(userImage);
        assertEquals(USER_IMAGE_TEST, savedUserImage.getImage());
        assertEquals(USER_ID, savedUserImage.getUser().getId());
    }

    @Test
    void update(){
        UserImage userImage = UserImage.builder()
                .image(USER_IMAGE_TEST)
                .user(userRepository.findById(USER_ID).orElse(null))
                .build();
        UserImage savedUserImage = userImageRepository.saveAndFlush(userImage);
        savedUserImage.setImage(USER_IMAGE_UPDATED);
        UserImage updatedUserImage = userImageRepository.saveAndFlush(savedUserImage);
        assertEquals(USER_IMAGE_UPDATED, updatedUserImage.getImage());
    }

    @Test
    void delete(){
        UserImage userImage = UserImage.builder()
                .image(USER_IMAGE_TEST)
                .user(userRepository.findById(USER_ID).orElse(null))
                .build();
        var savedUserImage = Optional.of(userImageRepository.saveAndFlush(userImage));
        assertTrue(userImageRepository.findById(savedUserImage.get().getId()).isPresent());
        userImageRepository.deleteById(savedUserImage.get().getId());
        assertFalse(userImageRepository.findById(savedUserImage.get().getId()).isPresent());
    }
}