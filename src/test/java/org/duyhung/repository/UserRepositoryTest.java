package org.duyhung.repository;

import jakarta.persistence.Table;
import org.duyhung.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void saveUser(){
        User user = new User();
        user.setCode("admin");
        user.setName("Nguyen Hung");
        user.setRole(true);
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        userRepository.save(user);
    }
}