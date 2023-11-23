package by.matveyvs.springdatajpatask.http.rest;

import by.matveyvs.springdatajpatask.dto.UserImageReadDto;
import by.matveyvs.springdatajpatask.service.UserImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userImages")
@RequiredArgsConstructor
public class UserImageRestController {
    private final UserImageService userImageService;

    @GetMapping("/{id}")
    public List<UserImageReadDto> findAllByUserId(@PathVariable("id") Long id) {
        return userImageService.findAllByUserId(id);
    }

    @GetMapping("/{imageId}/pics")
    public byte[] findByImageId(@PathVariable("imageId") Long id) {
        return userImageService.findAvatar(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
