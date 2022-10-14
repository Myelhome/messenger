package com.example.boot.controller;

import com.example.boot.entity.Message;
import com.example.boot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    MessageRepository messageRepository;


    @GetMapping()
    public String main(){
        return "greeting";
    }

    @GetMapping("/main")
    public String godMessage(@RequestParam(required = false) String filter, Model model) {

        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }
}
