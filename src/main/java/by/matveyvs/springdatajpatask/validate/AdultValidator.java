package by.matveyvs.springdatajpatask.validate;

import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class AdultValidator implements ConstraintValidator<AdultRestriction, UserCreateEditDto> {
    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now().minusYears(18);
        return value.getBirthDate().isBefore(now);
    }
}
