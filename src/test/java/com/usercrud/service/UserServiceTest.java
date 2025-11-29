package com.usercrud.service;

import com.usercrud.domain.User;
import com.usercrud.exception.UserNotFoundException;
import com.usercrud.exception.UsernameAlreadyExistsException;
import com.usercrud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "testuser", "test@example.com");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_UsernameAlreadyExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.createUser(testUser);
        });
        
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_EmptyUsername() {
        User invalidUser = new User("", "test@example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });
    }

    @Test
    void testCreateUser_EmptyEmail() {
        User invalidUser = new User("testuser", "");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.getUserById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(1L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(
            new User(1L, "user1", "user1@example.com"),
            new User(2L, "user2", "user2@example.com")
        );
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUser_Success() {
        User updatedData = new User(null, "newusername", "newemail@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.findByUsername("newusername")).thenReturn(Optional.empty());
        when(userRepository.update(any(User.class))).thenReturn(testUser);

        User result = userService.updateUser(1L, updatedData);

        assertNotNull(result);
        verify(userRepository).update(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User updatedData = new User(null, "newusername", "newemail@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, updatedData);
        });
    }

    @Test
    void testUpdateUser_UsernameAlreadyTaken() {
        User updatedData = new User(null, "existinguser", "newemail@example.com");
        User existingUser = new User(2L, "existinguser", "existing@example.com");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingUser));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            userService.updateUser(1L, updatedData);
        });
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(1L);
        });
        
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean exists = userService.userExists(1L);

        assertTrue(exists);
        verify(userRepository).existsById(1L);
    }
}
