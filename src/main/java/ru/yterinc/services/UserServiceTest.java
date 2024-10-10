package ru.yterinc.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yterinc.models.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testRegister() {
        assertTrue(userService.register("Alice", "password123", "alice@example.com"));
        assertFalse(userService.register("Bob", "password456", "alice@example.com")); // Duplicate email
    }

    @Test
    public void testLogin() {
        userService.register("Alice", "password123", "alice@example.com");
        User user = userService.login("alice@example.com", "password123");
        assertNotNull(user);
        assertEquals("Alice", user.getName());

        assertNull(userService.login("alice@example.com", "wrongpassword")); // Incorrect password
    }

    @Test
    public void testUpdateUser() {
        userService.register("Alice", "password123", "alice@example.com");
        User user = userService.getUser("alice@example.com");

        assertTrue(userService.updateUser(user, "Alice Updated", "newpassword123", "aliceupdated@example.com"));
        User updatedUser = userService.getUser("aliceupdated@example.com");
        assertNotNull(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        userService.register("Alice", "password123", "alice@example.com");
        userService.deleteUser("alice@example.com");
        assertNull(userService.getUser("alice@example.com"));
    }
}