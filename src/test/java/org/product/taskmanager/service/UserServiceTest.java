import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testGetAllUsers() {
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser.getId());
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john.doe@example.com", createdUser.getEmail());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane.doe@example.com");
        User createdUser = userService.createUser(user);

        Optional<User> foundUser = userService.getUserById(createdUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("Jane Doe", foundUser.get().getName());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        User createdUser = userService.createUser(user);

        User updatedUser = new User();
        updatedUser.setName("Alice Smith");
        updatedUser.setEmail("alice.smith@example.com");

        Optional<User> result = userService.updateUser(createdUser.getId(), updatedUser);
        assertTrue(result.isPresent());
        assertEquals("Alice Smith", result.get().getName());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("Bob");
        user.setEmail("bob@example.com");
        User createdUser = userService.createUser(user);

        boolean isDeleted = userService.deleteUser(createdUser.getId());
        assertTrue(isDeleted);
        assertFalse(userService.getUserById(createdUser.getId()).isPresent());
    }

    @Test
    public void testDeleteNonExistentUser() {
        boolean isDeleted = userService.deleteUser(999L);
        assertFalse(isDeleted);
    }
}