package by.matveyvs.springdatajpatask.mapper;

import by.matveyvs.springdatajpatask.dto.UserImageCreateEditDto;
import by.matveyvs.springdatajpatask.entity.User;
import by.matveyvs.springdatajpatask.entity.UserImage;
import by.matveyvs.springdatajpatask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserImageCreateEditMapper implements Mapper<UserImageCreateEditDto, UserImage> {
    private final UserRepository userRepository;

    @Override
    public UserImage map(UserImageCreateEditDto object) {
        UserImage userImage = new UserImage();
        copy(object, userImage);
        return userImage;
    }

    @Override
    public UserImage map(UserImageCreateEditDto fromObject, UserImage toObject) {
        copy(fromObject,toObject);
        return Mapper.super.map(fromObject, toObject);
    }

    private void copy(UserImageCreateEditDto object, UserImage userImage) {
        User user = getUser(object.getUserId());

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image ->
                    userImage.setImage(image.getOriginalFilename())
                );
        userImage.setUser(user);
    }

    private User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
