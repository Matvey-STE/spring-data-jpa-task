package by.matveyvs.springdatajpatask.service;

import by.matveyvs.springdatajpatask.dto.UserImageCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import by.matveyvs.springdatajpatask.entity.UserImage;
import by.matveyvs.springdatajpatask.mapper.UserImageCreateEditMapper;
import by.matveyvs.springdatajpatask.mapper.UserImageReadMapper;
import by.matveyvs.springdatajpatask.repository.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserImageService {
    private final ImageService imageService;
    private final UserImageRepository userImageRepository;
    private final UserImageReadMapper userImageReadMapper;
    private final UserImageCreateEditMapper userImageCreateEditMapper;

    public Optional<UserImageReadDto> findById(Long id) {
        return userImageRepository.findById(id)
                .map(userImageReadMapper::map);
    }

    public List<UserImageReadDto> findAllByUserId(Long userId) {
        return userImageRepository.findAllByUserId(userId)
                .stream().map(userImageReadMapper::map)
                .toList();
    }

    @Transactional
    public UserImageReadDto create(UserImageCreateEditDto userImageCreateEditDto) {
        return Optional.of(userImageCreateEditDto)
                .map(userImageCreateEditMapper::map)
                .map(userImageRepository::saveAndFlush)
                .map(userImageReadMapper::map)
                .orElseThrow();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userImageRepository.findById(id)
                .map(UserImage::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }


    @Transactional
    public Optional<UserImageReadDto> update(Long id, UserImageCreateEditDto userImageCreateEditDto) {
        return userImageRepository.findById(id)
                .map(entity -> userImageCreateEditMapper.map(userImageCreateEditDto, entity))
                .map(userImageRepository::saveAndFlush)
                .map(userImageReadMapper::map);
    }


    @Transactional
    public boolean deleteById(Long id) {
        return userImageRepository.findById(id)
                .map(entity -> {
                    userImageRepository.delete(entity);
                    userImageRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
