package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.entity.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
@AllArgsConstructor
class UserRepositoryTest {
    private UserRepository userRepository;

    @Test
    void findAllByRoleAndBetween2Dates() {
        LocalDate startDate = LocalDate.of(1980, 2, 1);
        LocalDate endDate = LocalDate.of(1990, 12, 31);
        userRepository.save(User
                .builder()
                .username("test")
                .firstname("Andrew")
                .birthDate(startDate)
                .build());
        List<User> allByRoleAndBirthDateBetween =
                userRepository.findAllByRoleAndBirthDateBetween(Role.ADMIN, startDate, endDate);
        assertFalse(allByRoleAndBirthDateBetween.isEmpty());
    }

    @Test
    void find4SortByBirthdate() {
        var test = userRepository.findFirst4By(Sort.by("birthDate").ascending());
        assertFalse(test.isEmpty());
        for (int i = 0; i < test.size() - 1; i++) {
            assertTrue(test.get(i).getBirthDate().isBefore((test.get(i + 1).getBirthDate())));
        }
    }

    @Test
    void find4SortByBirthdateAndFIO() {
        var test = userRepository.findFirst4By(
                Sort.by("birthDate")
                        .and(Sort.by("firstname")
                                .and(Sort.by("firstname")))
                        .ascending());
        assertFalse(test.isEmpty());
        for (int i = 0; i < test.size() - 1; i++) {
            assertTrue(test.get(i).getBirthDate().isBefore((test.get(i + 1).getBirthDate())));
        }
    }

    @Test
    void findByRoleUserPlusPaginationAndSortByUsername(){
        var sort = Sort.by("username");
        var pageable = PageRequest.of(0,1, sort);

        var page = userRepository.findAllByRole(Role.USER, pageable);
        assertEquals(2, page.getTotalPages());
        assertEquals(2, page.getTotalElements());
        page.forEach(user -> assertEquals(Role.USER, user.getRole()));
        while (page.hasNext()){
            page = userRepository.findAllByRole(Role.USER, page.nextPageable());
            page.forEach(user -> assertEquals(Role.USER, user.getRole()));
        }
    }
    @Test
    void findByRoleAdminPlusPaginationAndSortById(){
        var sort = Sort.by("id");
        var pageable = PageRequest.of(0,1, sort);

        var page = userRepository.findAllByRole(Role.ADMIN, pageable);
        assertEquals(1, page.getTotalPages());
        assertEquals(1, page.getTotalElements());
        page.forEach(user -> assertEquals(Role.ADMIN, user.getRole()));
        while (page.hasNext()){
            page = userRepository.findAllByRole(Role.USER, page.nextPageable());
            page.forEach(user -> assertEquals(Role.ADMIN, user.getRole()));
        }
    }
    @Test
    void findByRoleUserPlusPaginationAndSortByFirstName(){
        var sort = Sort.by("id");
        var pageable = PageRequest.of(0,2, sort);
        var page = userRepository.findAllByRole(Role.USER, pageable);
        assertEquals(1, page.getTotalPages());
        assertEquals(2, page.getTotalElements());
        page.forEach(user -> assertEquals(Role.USER, user.getRole()));
        while (page.hasNext()){
            page = userRepository.findAllByRole(Role.USER, page.nextPageable());
            page.forEach(user -> assertEquals(Role.USER, user.getRole()));
        }
    }
}
