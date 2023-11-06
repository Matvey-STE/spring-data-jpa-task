package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.dto.LoginDto;
import by.matveyvs.springdatajpatask.entity.Role;
import by.matveyvs.springdatajpatask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") LoginDto loginDto) {
        var userReadDto = userService
                .findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if (userReadDto.isPresent()) {
            if (userReadDto.get().getRole() == Role.ADMIN) {
                return "redirect:/users";
            } else {
                return "redirect:/users/" + userReadDto.get().getId();
            }
        }
        return "redirect:/login";
    }
}
