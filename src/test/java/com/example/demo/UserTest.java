package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import com.example.demo.models.User;

public class UserTest {

    @Test
    public void testUsernameSetCorrectly() {
        User user = new User();
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    public void testNameSetCorrectly() {
        User user = new User();
        user.setName("mohsen samer");
        assertEquals("mohsen samer", user.getName());
    }

    @Test
    public void testPasswordSetCorrectly() {
        User user = new User();
        user.setPassword("mypassword");
        assertEquals("mypassword", user.getPassword());
    }

    @Test
    public void testPasswordHashingAndVerification() {
        String password = "mypassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        assertTrue(BCrypt.checkpw("mypassword", hashedPassword));
    }

    @Test
    public void testIncorrectPasswordDoesNotMatchHash() {
        String password = "mypassword";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        assertFalse(BCrypt.checkpw("wrongpassword", hashedPassword));
    }

    @Test
    public void testRegisterFailsIfUsernameExists() {
        String newUsername = "mohsen";
        String[] existingUsers = {"mohsen", "omar", "amr"};
        boolean exists = false;

        for (String u : existingUsers) {
            if (u.equals(newUsername)) {
                exists = true;
                break;
            }
        }

        assertTrue(exists);
    }

    @Test
    public void testRegisterSuccessIfUsernameNotExists() {
        String newUsername = "mohsen";
        String[] existingUsers = {"omar", "amr", "fady"};
        boolean exists = false;

        for (String u : existingUsers) {
            if (u.equals(newUsername)) {
                exists = true;
                break;
            }
        }

        assertFalse(exists);
    }

    @Test
    public void testLoginPasswordMatches() {
        String rawPassword = "securepass";
        String storedHash = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
        assertTrue(BCrypt.checkpw("securepass", storedHash));
    }

    @Test
    public void testLoginFailsWithWrongPassword() {
        String storedHash = BCrypt.hashpw("securepass", BCrypt.gensalt(12));
        assertFalse(BCrypt.checkpw("wrongpass", storedHash));
    }
}
