package com.example.blog.services;

import com.example.blog.model.Friends;
import com.example.blog.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {
    List<Users> getAllUsers();

    boolean addUser(Users user);

    String validateUser(Users user);

    Optional<Users> getUserByEmail(String email);

    Users getUserById(Long userId);

    void deleteUser(Long userId);

    void connectFriends(Long userId, Long friendId);

    List<Friends> getAllFriendByUserId(Long userId);
}
