package by.matveyvs.springdatajpatask.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class UserImageCreateEditDto {
    MultipartFile image;
    Long userId;
}
