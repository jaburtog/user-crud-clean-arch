package com.usercrud.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User domain entity.
 */
class UserTest {

    @Test
    void testUserCreationWithAllFields() {
        User user = new User(1L, "testuser", "test@example.com");
        
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testUserCreationWithoutId() {
        User user = new User("testuser", "test@example.com");
        
        assertNull(user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testUserSetters() {
        User user = new User();
        
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testUserEquality() {
        User user1 = new User(1L, "testuser", "test@example.com");
        User user2 = new User(1L, "differentuser", "different@example.com");
        User user3 = new User(2L, "testuser", "test@example.com");
        
        assertEquals(user1, user2); // Same ID
        assertNotEquals(user1, user3); // Different ID
    }

    @Test
    void testUserHashCode() {
        User user1 = new User(1L, "testuser", "test@example.com");
        User user2 = new User(1L, "differentuser", "different@example.com");
        
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUserToString() {
        User user = new User(1L, "testuser", "test@example.com");
        String toString = user.toString();
        
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("test@example.com"));
    }
}
