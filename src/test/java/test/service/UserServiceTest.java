package test.service;

import com.user.domain.User;
import com.user.dto.RegisterRequest;
import com.user.dto.UserResponse;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository repo;
    @Mock PasswordEncoder encoder;

    @InjectMocks UserService service;

    @Test
    void registerSucceeds() {
        // Arrange
        RegisterRequest req = new RegisterRequest("john", "john@example.com", "Secret123!", "John Doe");

        when(repo.existsByUsername("john")).thenReturn(false);
        when(repo.existsByEmail("john@example.com")).thenReturn(false);
        when(encoder.encode("Secret123!")).thenReturn("hashed");

        // Act
        UserResponse resp = service.register(req);

        // Assert response
        assertEquals("john", resp.getUsername());
        assertEquals("john@example.com", resp.getEmail());
        assertEquals("John Doe", resp.getFullName());

        // Assert saved entity
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repo).save(captor.capture());
        User saved = captor.getValue();
        assertEquals("john", saved.getUsername());
        assertEquals("hashed", saved.getPasswordHash());

        // Verify encoder was used
        verify(encoder).encode("Secret123!");
    }

    @Test
    void registerFailsOnDuplicateUsername() {
        RegisterRequest req = new RegisterRequest("john", "john@example.com", "Secret123!", "John Doe");

        when(repo.existsByUsername("john")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.register(req));
        verify(repo, never()).save(any());
    }

    @Test
    void registerFailsOnDuplicateEmail() {
        RegisterRequest req = new RegisterRequest("john", "john@example.com", "Secret123!", "John Doe");

        when(repo.existsByUsername("john")).thenReturn(false);
        when(repo.existsByEmail("john@example.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.register(req));
        verify(repo, never()).save(any());
    }
}