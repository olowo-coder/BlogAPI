package com.example.blog.services.serviceImpl;

import com.example.blog.model.Users;
import com.example.blog.repository.UserRepository;
import com.example.blog.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UsersService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean addUser(Users user){
        Optional<Users> usersOptional = userRepository.findByEmail(user.getEmail());
        if(usersOptional.isPresent()){
            return true;
        }
        userRepository.save(user);
        return false;
    }

    public String  validateUser(Users user) {
        Optional<Users> user1 = userRepository.findByEmail(user.getEmail());
        if (user1.isEmpty()) {
            return "Not Registered Email";
        }
        if (!user1.get().getPassword().equals(user.getPassword())) {
            return "Incorrect Password";
        }
        return "valid";
    }

    public Optional<Users> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Users getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    @Scheduled(fixedDelay = 10000)  // 10 Seconds
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
