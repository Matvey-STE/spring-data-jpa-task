package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.dto.UserTestDto;
import by.matveyvs.springdatajpatask.entity.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"user"})
public class GreetingController {
    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(UserTestDto userTestDto, Model model) {
        model.addAttribute("user", userTestDto);
        return "greeting/hello";
    }

    @GetMapping("/hello/{id}")
    public String hello(
            @RequestParam(required = false) String age,
            @RequestHeader String accept,
            @CookieValue("JSESSIONID") String jsessionId,
            @PathVariable("id") Integer id,
            Model model, UserTestDto user) {
        model.addAttribute("user", user);
        return "greeting/hello";
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserTestDto user) {
        return "greeting/bye";
    }
}
