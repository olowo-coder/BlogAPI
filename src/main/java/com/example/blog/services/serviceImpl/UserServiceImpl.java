package com.example.blog.services.serviceImpl;

import com.example.blog.model.Friends;
import com.example.blog.model.Users;
import com.example.blog.repository.FriendsRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.friendsRepository = friendsRepository;
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

    @Override
    public void connectFriends(Long userId, Long friendId) {
        Users user = getUserById(userId);
        Users friend = getUserById(friendId);
        Friends connections = new Friends();
        connections.setUsers(user);
        connections.setFriends(friend);
        friendsRepository.save(connections);
    }
}
