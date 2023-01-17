package com.example.boot.controller;

import com.example.boot.entity.Credential;
import com.example.boot.entity.Message;
import com.example.boot.repository.MessageRepository;
import com.example.boot.service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ErrorService errorService;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/insert")
    public String add(
            @AuthenticationPrincipal Credential credential,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

        message.setAuthor(credential.getUser());

        if (bindingResult.hasErrors()) {
            model.mergeAttributes(errorService.getErrors(bindingResult));
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFileName));


                message.setFilename(resultFileName);
            }

            messageRepository.save(message);

            model.addAttribute("message", null);
        }

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }
}
