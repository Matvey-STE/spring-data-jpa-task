package by.matveyvs.springdatajpatask.service;

import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserImageCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import by.matveyvs.springdatajpatask.dto.UserReadDto;
import by.matveyvs.springdatajpatask.entity.User;
import by.matveyvs.springdatajpatask.entity.UserImage;
import by.matveyvs.springdatajpatask.mapper.UserCreateEditMapper;
import by.matveyvs.springdatajpatask.mapper.UserImageReadMapper;
import by.matveyvs.springdatajpatask.mapper.UserReadMapper;
import by.matveyvs.springdatajpatask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final ImageService imageService;
    private final UserImageReadMapper userImageReadMapper;
    private final UserImageService userImageService;
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    private final UserCreateEditMapper userCreateEditMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<UserReadDto> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto);
                })
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
    @Transactional
    @SneakyThrows
    public Long addUserImage(Long userId, MultipartFile image) {
        uploadImage(image);
        UserImageReadDto userImageReadDto = userImageService.create(new UserImageCreateEditDto(image, userId));
        UserImage map = userImageReadMapper.map(userImageReadDto);
        userRepository.findById(userId)
                .map(user -> {
                    user.addUserImage(map);
                    return userRepository.saveAndFlush(user);
                });
        return map.getId();
    }
    @Transactional
    public Optional<UserReadDto> removeUserImage(Long userId, Long userImageId) {
        var userImage =
                userImageService.findById(userImageId)
                        .map(userImageReadMapper::map).orElseThrow();

        return userRepository.findById(userId)
                .map(user -> {
                    user.removeUserImage(userImage);
                    userImageService.deleteById(userImageId);
                    userRepository.saveAndFlush(user);
                    return user;
                })
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
