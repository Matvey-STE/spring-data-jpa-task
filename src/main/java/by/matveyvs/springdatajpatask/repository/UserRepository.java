package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByRoleAndBirthDateBetween(Role role, LocalDate startDate, LocalDate endDate);

}
