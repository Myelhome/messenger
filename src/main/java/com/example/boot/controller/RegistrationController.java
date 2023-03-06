package com.example.boot.controller;

import com.example.boot.dto.CaptchaResponse;
import com.example.boot.entity.User;
import com.example.boot.service.ErrorService;
import com.example.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;
    @Autowired
    private ErrorService errorService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponse response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);

        if(!response.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(errorService.getErrors(bindingResult));
        }

        boolean successDB = false;

        if(!bindingResult.hasErrors() && response.isSuccess()) {
            successDB = userService.addUser(user);

            if (!successDB) {
                model.addAttribute("usernameError", "ERROR: user with \"" + user.getUsername() + "\" already exists");
            }
        }

        if(bindingResult.hasErrors() || !response.isSuccess() || !successDB){
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
