package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    List<UserImage> findAllByUserId(Long userId);
}
