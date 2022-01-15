package com.example.blog.services;

import com.example.blog.dto.UsersDto;
import com.example.blog.model.Friends;
import com.example.blog.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public interface UsersService {
    List<Users> getAllUsers();

    Page<Users> pageGetAllUsers(String name, int next);

    boolean addUser(UsersDto user);

    String validateUser(Users user);

    Optional<Users> getUserByEmail(String email);

    Users getUserById(Long userId);

    CompletableFuture<String> deleteUser(Long userId) throws InterruptedException;

    void connectFriends(Long userId, Long friendId);

    List<Friends> getAllFriendByUserId(Long userId);
}
