package by.matveyvs.springdatajpatask.mapper;

import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import by.matveyvs.springdatajpatask.entity.UserImage;
import org.springframework.stereotype.Component;

@Component
public class UserImageReadMapper implements Mapper<UserImage, UserImageReadDto> {

    @Override
    public UserImageReadDto map(UserImage object) {
        return new UserImageReadDto(object.getId(),
                object.getImage(), object.getUser());
    }

    public UserImage map(UserImageReadDto userImageReadDto) {
        return UserImage.builder()
                .id(userImageReadDto.getId())
                .image(userImageReadDto.getImage())
                .user(userImageReadDto.getUser())
                .build();
    }
}
