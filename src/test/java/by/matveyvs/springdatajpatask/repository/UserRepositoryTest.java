package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.entity.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@IT
@AllArgsConstructor
class UserRepositoryTest {
    private UserRepository userRepository;

    @Test
    void findAllByRole() {
        LocalDate startDate = LocalDate.of(1980, 2, 1);
        LocalDate endDate = LocalDate.of(1990, 12, 31);
        User andrew = userRepository.save(User
                .builder()
                .username("test")
                .firstname("Andrew")
                .birthDate(startDate)
                .build());
        List<User> allByRoleAndBirthDateBetween =
                userRepository.findAllByRoleAndBirthDateBetween(Role.ADMIN, startDate, endDate);
        assertFalse(allByRoleAndBirthDateBetween.isEmpty());
    }
}
