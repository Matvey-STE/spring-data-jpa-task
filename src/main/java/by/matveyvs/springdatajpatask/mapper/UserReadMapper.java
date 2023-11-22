package by.matveyvs.springdatajpatask.mapper;

import by.matveyvs.springdatajpatask.dto.CompanyReadDto;
import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import by.matveyvs.springdatajpatask.dto.UserReadDto;
import by.matveyvs.springdatajpatask.entity.User;
import by.matveyvs.springdatajpatask.entity.UserImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {
    private final CompanyReadMapper companyReadMapper;
    private final UserImageReadMapper userImageReadMapper;

    @Override
    public UserReadDto map(User object) {
        CompanyReadDto company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);

        return new UserReadDto(
                        object.getId(),
                        object.getUsername(),
                        object.getPassword(),
                        object.getBirthDate(),
                        object.getFirstname(),
                        object.getLastname(),
                        object.getRole(),
                        object.getImage(),
                company,
                getUserImageList(object.getUserImages())
                );
    }
    public List<UserImageReadDto> getUserImageList(List<UserImage> listOfUserImages){
        return listOfUserImages.stream().map(userImageReadMapper::map).toList();
    }
}
