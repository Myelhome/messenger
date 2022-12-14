package com.example.boot.repository;

import com.example.boot.entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag, Sort sort);
    List<Message> findAll(Sort sort);
}
