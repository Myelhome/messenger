package com.example.boot.service;

import com.example.boot.entity.Role;
import com.example.boot.entity.User;
import com.example.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderCustom mailSenderCustom;

    @Value("${server.endpoint}")
    String endpoint;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) return false;

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        if (StringUtils.hasLength(user.getEmail())){

            String message = String.format("Hello, %s! \n" +
                    "Welcome to Shitter. Yaaay. Plese visit next link: %s/activate/%s",
                    user.getUsername(), endpoint, user.getActivationCode()
            );

            mailSenderCustom.sendMessage(user.getEmail(), "SHITTER EMAIL CONFIRMATION", message);
        }

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
}
