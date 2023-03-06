package com.example.boot.service;

import com.example.boot.entity.Role;
import com.example.boot.entity.User;
import com.example.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSenderCustom mailSenderCustom;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${server.endpoint}")
    String socket;

    public boolean addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (StringUtils.hasLength(user.getEmail())) notify(user, " to finish registration", user.getEmail(), "activate");

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) return false;

        user.setActivationCode(null);
        user.setActive(true);

        userRepository.save(user);

        return true;
    }

    public boolean activateEmail(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) return false;

        user.setActivationCode(null);
        user.setEmail(user.getEmailNew());
        user.setEmailNew(null);

        userRepository.save(user);

        return true;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void updateUser(User user, String username, String password, Map<String, String> form) {
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        Set<Role> newRoles = new HashSet<>();
        form.keySet().stream().filter(roles::contains).forEach(r -> newRoles.add(Role.valueOf(r)));

        user.setRoles(newRoles);

        userRepository.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        boolean emailChanged = (email != null && !email.equals(user.getEmail()));

        if (emailChanged) {
            user.setEmailNew(email);
            user.setActivationCode(UUID.randomUUID().toString());
        }

        if (StringUtils.hasLength(password)) user.setPassword(password);

        userRepository.save(user);

        if(emailChanged) notify(user, " to change email to the current", email, "email");
    }

    private void notify(User user, String action, String mail, String endpoint) {
        String message = String.format("Hello, %s! \n" +
                        "Welcome to Shitter. Yaaay. Plese visit next link%s: %s/%s/%s",
                user.getUsername(), action, socket, endpoint, user.getActivationCode()
        );

        mailSenderCustom.sendMessage(mail, "SHITTER EMAIL CONFIRMATION", message);
    }
}
