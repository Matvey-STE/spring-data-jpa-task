package by.matveyvs.springdatajpatask.dto;

import by.matveyvs.springdatajpatask.entity.User;
import lombok.Value;

@Value
public class UserImageReadDto {
    Long id;
    String image;
    User user;
}
