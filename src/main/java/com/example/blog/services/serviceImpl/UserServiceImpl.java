package com.example.blog.services.serviceImpl;

import com.example.blog.dto.UsersDto;
import com.example.blog.model.Friends;
import com.example.blog.model.Users;
import com.example.blog.repository.FriendsRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;
    private Map<Long, String> listOfuserIdForDeactivate = new HashMap();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.friendsRepository = friendsRepository;
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Page<Users> pageGetAllUsers(String byWhat, int next){
        Pageable firstPageElements = PageRequest.of(0, next, Sort.by(byWhat).ascending());
        return userRepository.findAll(firstPageElements);
    }

    public boolean addUser(UsersDto user){
        Optional<Users> usersOptional = userRepository.findByEmail(user.getEmail());
        if(usersOptional.isPresent()){
            return true;
        }
        Users user1 = new Users();
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setPhone(user.getPhone());
        user1.setPassword(user.getPassword());
        user1.setStatus(user.getStatus());
        userRepository.save(user1);
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
        user1.get().setStatus(true);
        return "valid";
    }

    public Optional<Users> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Users getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }


@Async
    public CompletableFuture<String> deleteUser(Long userId) throws InterruptedException {
        listOfuserIdForDeactivate.put(userId, getUserById(userId).getEmail());
        log.info("Counting down to delete");
        Thread.sleep(30000L); // 30 sec
        if (userRepository.findById(userId).get().getStatus()){
            log.info("inside here");
            log.info("executed");
            listOfuserIdForDeactivate.remove(userId);
            return CompletableFuture.completedFuture("restored");
        }
        log.info("actually done deleting");
        userRepository.deleteById(userId);
        listOfuserIdForDeactivate.remove(userId);
        return CompletableFuture.completedFuture("deleted");
    }

    @Override
    public void connectFriends(Long userId, Long friendId) {
        Users friend = getUserById(friendId);
        Friends connections = new Friends();
        connections.setUser(userId);
        connections.setFriends(friend);
        friendsRepository.save(connections);
    }

    @Override
    public List<Friends> getAllFriendByUserId(Long userId) {
        return friendsRepository.getAllByUser(userId);
    }
}
