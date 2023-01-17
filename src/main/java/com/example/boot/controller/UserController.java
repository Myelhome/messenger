package com.example.boot.controller;

import com.example.boot.entity.Credential;
import com.example.boot.entity.Role;
import com.example.boot.entity.User;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userUpdate(@RequestParam("userId") User user,
                             @RequestParam Map<String, String> form,
                             @RequestParam String username,
                             @RequestParam String password) {
        userService.updateUser(user, username, password, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal Credential credential) {
        model.addAttribute("username", credential.getUser().getUsername());
        model.addAttribute("email", credential.getUser().getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal Credential credential,
                                @RequestParam String password,
                                @RequestParam String email){
        userService.updateProfile(credential.getUser(), password, email);
            return "redirect:/user/profile";
    }
}
