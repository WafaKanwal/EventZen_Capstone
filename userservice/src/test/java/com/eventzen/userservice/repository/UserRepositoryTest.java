package com.eventzen.userservice.repository;

import com.eventzen.userservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User user = new User();
        user.setName("Rahul");
        user.setEmail("rahul@example.com");
        user.setPassword("Password@123");

        savedUser = userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test saving a user")
    void testSaveUser() {
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Rahul");
        assertThat(savedUser.getEmail()).isEqualTo("rahul@example.com");
    }

    @Test
    @DisplayName("Test finding user by ID")
    void testFindById() {
        Optional<User> found = userRepository.findById(savedUser.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Rahul");
    }

    @Test
    @DisplayName("Test finding all users")
    void testFindAll() {
        User user2 = new User();
        user2.setName("Amit");
        user2.setEmail("amit@example.com");
        user2.setPassword("Password@123");

        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    @DisplayName("Test updating a user")
    void testUpdateUser() {
        savedUser.setName("Rohit");
        savedUser.setEmail("rohit@example.com");

        User updated = userRepository.save(savedUser);

        assertThat(updated.getName()).isEqualTo("Rohit");
        assertThat(updated.getEmail()).isEqualTo("rohit@example.com");
    }

    @Test
    @DisplayName("Test deleting a user")
    void testDeleteUser() {
        userRepository.deleteById(savedUser.getId());
        Optional<User> found = userRepository.findById(savedUser.getId());
        assertThat(found).isEmpty();
    }
}