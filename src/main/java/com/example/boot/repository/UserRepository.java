package com.example.boot.repository;

import com.example.boot.entity.Message;
import com.example.boot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String activationCode);
}
