package com.eventzen.userservice.service;

import com.eventzen.userservice.model.User;
import com.eventzen.userservice.repository.UserRepository;
import com.eventzen.userservice.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("Password123");
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(1L);

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    public void testGetAllUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane@example.com");

        List<User> users = Arrays.asList(user, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertEquals(2, allUsers.size());
        assertEquals("John Doe", allUsers.get(0).getName());
        assertEquals("Jane Smith", allUsers.get(1).getName());
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        user.setName("John Updated");
        User updatedUser = userService.updateUser(1L, user);

        assertEquals("John Updated", updatedUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, user));
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        lenient().when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}