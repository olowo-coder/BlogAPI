package com.example.blog.services;

import com.example.blog.model.Users;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface UsersService {
    List<Users> getAllUsers();

    boolean addUser(Users user);

    String validateUser(Users user);

    Optional<Users> getUserByEmail(String email);

    Users getUserById(Long userId);

    void deleteUser(Long userId);
}
