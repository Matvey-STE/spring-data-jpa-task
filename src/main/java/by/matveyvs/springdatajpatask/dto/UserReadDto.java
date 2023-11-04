package by.matveyvs.springdatajpatask.dto;

import by.matveyvs.springdatajpatask.entity.Role;
import lombok.Value;

import java.time.LocalDate;
@Value
public class UserReadDto {
    Long id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    CompanyReadDto companyReadDto;
}
