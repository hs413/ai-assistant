package ai.example.jwt;

import io.jsonwebtoken.Claims;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateToken() {
        // Given
        Map<String, Object> claims = Map.of("role", "USER");
        String subject = "user123";

        // When
        String token = jwtUtil.generateToken(claims, subject);

        // Then
        assertNotNull(token, "토큰 생성 실패!");
    }

    @Test
    void testValidateToken() {
        // Given
        Map<String, Object> claims = Map.of("role", "USER");
        String subject = "user123";
        String token = jwtUtil.generateToken(claims, subject);

        // When
        Claims extractedClaims = jwtUtil.validateToken(token);

        // Then
        assertNotNull(extractedClaims, "클레임이 null입니다!");
        assertEquals(subject, extractedClaims.getSubject(), "Subject가 일치하지 않습니다!");
        assertEquals("USER", extractedClaims.get("role"), "Claims에 기대 값이 없습니다!");
    }

    @Test
    void testExtractUsername() {
        // Given
        String subject = "user123";
        String token = jwtUtil.generateToken(Map.of(), subject);

        // When
        String extractedSubject = jwtUtil.extractUsername(token);

        // Then
        assertEquals(subject, extractedSubject, "Username(Subject)이 올바르지 않습니다!");
    }

    @Test
    void testIsTokenExpired_validToken() {
        // Given
        String token = jwtUtil.generateToken(Map.of(), "user123");

        // When
        boolean isExpired = jwtUtil.isTokenExpired(token);

        // Then
        assertFalse(isExpired, "토큰이 유효하지만 만료로 표시됩니다!");
    }

    @Test
    void testIsTokenExpired_expiredToken() throws InterruptedException {
        // Given
        String token = jwtUtil.generateToken(Map.of(), "user123", 1L);

        // Wait for token to expire
        Thread.sleep(10);
jwtUtil.validateToken(token);
        // When
        boolean isExpired = jwtUtil.isTokenExpired(token);

        // Then
        assertTrue(isExpired, "만료된 토큰이 만료되지 않은 것으로 표시됩니다!");
    }

    @Test
    void testInvalidToken_shouldThrowException() {
        // Given
        String invalidToken = "invalid.token.value";

        // When & Then
        assertThrows(IllegalStateException.class, () -> jwtUtil.validateToken(invalidToken),
                "잘못된 토큰을 검증에 성공했습니다!");
    }


}