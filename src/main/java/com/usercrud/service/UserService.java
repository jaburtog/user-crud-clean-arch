package com.usercrud.service;

import com.usercrud.domain.User;
import com.usercrud.exception.UserNotFoundException;
import com.usercrud.exception.UsernameAlreadyExistsException;
import com.usercrud.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

/**
 * Application service for User CRUD operations.
 * This class contains the business logic for user management.
 */
@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     * @throws IllegalArgumentException if username or email is empty
     * @throws UsernameAlreadyExistsException if username already exists
     */
    public User createUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        return userRepository.save(user);
    }

    /**
     * Gets a user by id.
     *
     * @param id the user id
     * @return an Optional containing the user if found
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Gets a user by username.
     *
     * @param username the username
     * @return an Optional containing the user if found
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Gets all users.
     *
     * @return list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing user.
     *
     * @param id   the user id
     * @param user the user data to update
     * @return the updated user
     * @throws UserNotFoundException if user not found
     * @throws UsernameAlreadyExistsException if new username is already taken
     */
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            // Check if new username is already taken by another user
            Optional<User> userWithSameUsername = userRepository.findByUsername(user.getUsername());
            if (userWithSameUsername.isPresent() && !userWithSameUsername.get().getId().equals(id)) {
                throw new UsernameAlreadyExistsException(user.getUsername());
            }
            existingUser.setUsername(user.getUsername());
        }

        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }

        return userRepository.update(existingUser);
    }

    /**
     * Deletes a user by id.
     *
     * @param id the user id
     * @throws UserNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Checks if a user exists by id.
     *
     * @param id the user id
     * @return true if exists, false otherwise
     */
    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
