package com.example.boot.controller;

import com.example.boot.entity.User;
import com.example.boot.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        boolean success = userService.addUser(user);

        if (!success) {
            model.addAttribute("err", "ERROR: user with \"" + user.getUsername() + "\" already exists");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActive = userService.activateUser(code);

        if(isActive){
            model.addAttribute("message", "Yay user has been authorized!");
        }else {
            model.addAttribute("message", "Wrong activation code!");
        }
        return "login";
    }
}
