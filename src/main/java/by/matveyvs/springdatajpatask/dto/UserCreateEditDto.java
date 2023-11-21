package by.matveyvs.springdatajpatask.dto;

import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.validate.AdultRestriction;
import jakarta.validation.constraints.Email;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@FieldNameConstants
@AdultRestriction
public class UserCreateEditDto {
    @Email(message = "Username should be email format")
    String username;
    String password;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate;
    String firstname;
    String lastname;
    Role role;
    Integer companyId;
    MultipartFile image;
}
