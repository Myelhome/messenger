package com.example.boot.controller;

import com.example.boot.entity.User;
import com.example.boot.service.ErrorService;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private ErrorService errorService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.mergeAttributes(errorService.getErrors(bindingResult));
            model.addAttribute("user", user);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "ERROR: user with \"" + user.getUsername() + "\" already exists");
            model.addAttribute("user", user);
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActive = userService.activateUser(code);

        if (isActive) {
            model.addAttribute("message", "Yay user has been authorized!");
        } else {
            model.addAttribute("error", "Wrong activation code!");
        }
        return "login";
    }

    @GetMapping("/email/{code}")
    public String emailChange(Model model, @PathVariable String code) {
        boolean isActive = userService.activateEmail(code);

        if (isActive) {
            model.addAttribute("message", "Yay email has been changed!");
        } else {
            model.addAttribute("error", "Wrong activation code!");
        }
        return "redirect:/user/profile";
    }
}
