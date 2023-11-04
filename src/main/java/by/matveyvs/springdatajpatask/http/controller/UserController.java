package by.matveyvs.springdatajpatask.http.controller;

import by.matveyvs.springdatajpatask.dto.UserCreateEditDto;
import by.matveyvs.springdatajpatask.dto.UserReadDto;
import by.matveyvs.springdatajpatask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public String create(@ModelAttribute UserCreateEditDto user){
        UserReadDto dto = userService.create(user);
        return "redirect:/users/" + dto.getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute UserCreateEditDto user){
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        if(!userService.deleteById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}
