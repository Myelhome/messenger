package com.example.boot.controller;

import com.example.boot.entity.Credential;
import com.example.boot.entity.Role;
import com.example.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String account(@AuthenticationPrincipal Credential credential,
                          Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", credential.getUser());
        return "userEdit";
    }
}
