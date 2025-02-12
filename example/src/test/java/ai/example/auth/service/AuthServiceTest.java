package ai.example.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ai.example.auth.dto.LoginRequest;
import ai.example.auth.dto.LoginResponse;
import ai.example.jwt.JwtUtil;
import ai.example.user.model.User;
import ai.example.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private JwtUtil jwtUtil;

    private LoginRequest loginRequest;
    private User mockUser;

    @BeforeEach
    public void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encryptedPassword");
    }

    @Test
    public void givenValidRequest_whenLogin_thenReturnsToken() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(any(), anyString(), anyLong())).thenReturn("token");

        LoginResponse response = authService.login(loginRequest);

        assertEquals("token", response.getToken());
    }

    @Test
    public void givenNonExistingUser_whenLogin_thenThrowsIllegalArgumentException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequest));
    }

    @Test
    public void givenInvalidPassword_whenLogin_thenThrowsIllegalArgumentException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequest));
    }
}