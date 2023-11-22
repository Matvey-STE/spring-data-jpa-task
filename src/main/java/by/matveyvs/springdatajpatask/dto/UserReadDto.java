package by.matveyvs.springdatajpatask.dto;

import by.matveyvs.springdatajpatask.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.List;

@Value
@FieldNameConstants
public class UserReadDto {
    Long id;
    String username;
    String password;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    String image;
    CompanyReadDto companyReadDto;
    List<UserImageReadDto> userImageReadDtoList;
}
