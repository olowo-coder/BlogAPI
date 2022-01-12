package com.example.blog.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blog.model.Friends;
import com.example.blog.model.Users;
import com.example.blog.repository.FriendsRepository;
import com.example.blog.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private FriendsRepository friendsRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetAllUsers() {
        ArrayList<Users> usersList = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(usersList);
        List<Users> actualAllUsers = this.userServiceImpl.getAllUsers();
        assertSame(usersList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    void testAddUser() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);
        assertTrue(this.userServiceImpl.addUser(users1));
        verify(this.userRepository).findByEmail((String) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testAddUser2() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        when(this.userRepository.save((Users) any())).thenReturn(users);
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);
        assertFalse(this.userServiceImpl.addUser(users1));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.userRepository).save((Users) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testValidateUser() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);
        assertEquals("valid", this.userServiceImpl.validateUser(users1));
        verify(this.userRepository).findByEmail((String) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testValidateUser2() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("valid");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);
        assertEquals("Incorrect Password", this.userServiceImpl.validateUser(users1));
        verify(this.userRepository).findByEmail((String) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testValidateUser3() {
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());

        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        assertEquals("Not Registered Email", this.userServiceImpl.validateUser(users));
        verify(this.userRepository).findByEmail((String) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetUserByEmail() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        Optional<Users> actualUserByEmail = this.userServiceImpl.getUserByEmail("jane.doe@example.org");
        assertSame(ofResult, actualUserByEmail);
        assertTrue(actualUserByEmail.isPresent());
        verify(this.userRepository).findByEmail((String) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetUserById() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(users, this.userServiceImpl.getUserById(123L));
        verify(this.userRepository).findById((Long) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(this.userRepository).deleteById((Long) any());
        this.userServiceImpl.deleteUser(123L);
        verify(this.userRepository).deleteById((Long) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testConnectFriends() {
        Users users = new Users();
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhone("4105551212");
        users.setUserId(123L);
        Optional<Users> ofResult = Optional.of(users);
        when(this.userRepository.findById((Long) any())).thenReturn(ofResult);

        Users users1 = new Users();
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhone("4105551212");
        users1.setUserId(123L);

        Friends friends = new Friends();
        friends.setFriends(users1);
        friends.setFriendsId(123L);
        friends.setUser(1L);
        when(this.friendsRepository.save((Friends) any())).thenReturn(friends);
        this.userServiceImpl.connectFriends(123L, 123L);
        verify(this.userRepository).findById((Long) any());
        verify(this.friendsRepository).save((Friends) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }

    @Test
    void testGetAllFriendByUserId() {
        ArrayList<Friends> friendsList = new ArrayList<>();
        when(this.friendsRepository.getAllByUser((Long) any())).thenReturn(friendsList);
        List<Friends> actualAllFriendByUserId = this.userServiceImpl.getAllFriendByUserId(123L);
        assertSame(friendsList, actualAllFriendByUserId);
        assertTrue(actualAllFriendByUserId.isEmpty());
        verify(this.friendsRepository).getAllByUser((Long) any());
        assertTrue(this.userServiceImpl.getAllUsers().isEmpty());
    }
}

