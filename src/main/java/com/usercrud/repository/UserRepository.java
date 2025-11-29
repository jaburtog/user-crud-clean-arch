package com.usercrud.repository;

import com.usercrud.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * This interface defines the contract for user persistence operations.
 */
public interface UserRepository {

    /**
     * Saves a new user or updates an existing one.
     *
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);

    /**
     * Finds a user by id.
     *
     * @param id the user id
     * @return an Optional containing the user if found
     */
    Optional<User> findById(Long id);

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds all users.
     *
     * @return list of all users
     */
    List<User> findAll();

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @return the updated user
     */
    User update(User user);

    /**
     * Deletes a user by id.
     *
     * @param id the user id
     */
    void deleteById(Long id);

    /**
     * Checks if a user exists by id.
     *
     * @param id the user id
     * @return true if exists, false otherwise
     */
    boolean existsById(Long id);
}
