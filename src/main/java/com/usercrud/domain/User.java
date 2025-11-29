package com.usercrud.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * User domain entity representing a user in the system.
 * This class follows Clean Architecture principles by being independent of frameworks.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Default constructor required by JPA.
     */
    public User() {
    }

    /**
     * Constructor with username and email.
     *
     * @param username the username
     * @param email    the email
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    /**
     * Constructor with all fields.
     *
     * @param id       the id
     * @param username the username
     * @param email    the email
     */
    public User(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
