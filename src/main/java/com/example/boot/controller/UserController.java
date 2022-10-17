package com.example.boot.controller;

import com.example.boot.entity.Role;
import com.example.boot.entity.User;
import com.example.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping
    public String userUpdate(@RequestParam("userId") User user,
                             @RequestParam Map<String, String> form,
                             @RequestParam String username,
                             @RequestParam String password){
        user.setUsername(username);
        user.setPassword(password);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        Set<Role> newRoles = new HashSet<>();
        form.keySet().stream().filter(roles::contains).forEach(r -> newRoles.add(Role.valueOf(r)));

        user.setRoles(newRoles);

        userRepository.save(user);
        return "redirect:/user";
    }
}
