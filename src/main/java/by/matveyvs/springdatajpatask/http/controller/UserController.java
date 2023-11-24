package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserImageCreateEditDto;
import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.exception.IllegalAgeException;
import by.matveyvs.springdatajpatask.service.CompanyService;
import by.matveyvs.springdatajpatask.service.UserImageService;
import by.matveyvs.springdatajpatask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserImageService userImageService;
    private final UserService userService;
    private final CompanyService companyService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{userId}/userImages")
    public String findUserImagesByUserId(@PathVariable("userId") Long id,
                                         Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("imageList", userImageService.findAllByUserId(id));

        return "/user/userImage";
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto userDto) {
        model.addAttribute("user", userDto);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        return "user/registration";

    }

    @PostMapping
    public String create(@ModelAttribute @Validated UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            if (isValidHasAgeRestriction(bindingResult)) {
                throw new IllegalAgeException();
            }
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        var userReadDto = userService.create(user);
        return "redirect:/users/" + userReadDto.getId();
    }

    @PostMapping("/{userId}/update")
    public String update(@ModelAttribute @Validated UserCreateEditDto user,
                         @PathVariable("userId") Long userId) {
        return userService.update(userId, user)
                .map(updatedUser -> "redirect:/users/" + userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Update failed"));
    }

    private boolean isValidHasAgeRestriction(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (Objects.equals(error.getCode(), "AdultRestriction")) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/{userId}/userImages/addImage")
    public String addImage(@PathVariable("userId") Long userId,
                           @Validated @ModelAttribute UserImageCreateEditDto imageCreateEditDto) {
        if (!imageCreateEditDto.getImage().isEmpty()) {
            userService.addUserImage(userId, imageCreateEditDto.getImage());
        }
        return "redirect:/users/" + userId + "/userImages";
    }

    @PostMapping("/{userId}/userImages/{imageId}/removeImage")
    public String removeImage(@PathVariable("userId") Long userId,
                              @PathVariable("imageId") Long imageId,
                              @ModelAttribute UserImageCreateEditDto imageCreateEditDto) {
        userService.removeUserImage(userId, imageId);
        return "redirect:/users/" + userId + "/userImages";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}
