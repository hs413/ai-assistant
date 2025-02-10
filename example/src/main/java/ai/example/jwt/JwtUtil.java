package ai.example.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // 시크릿 키로 서명 – 안전하게 관리해야 합니다 (환경 변수 또는 Vault 사용 권장)
//    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final Key secretKey = Keys.hmacShaKeyFor("fixed-secret-key-for-testing-purpose".getBytes());

    // 토큰 유효 시간 (1시간 설정)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    /**
     * JWT 토큰 생성
     * @param claims 사용자에 대한 클레임 정보
     * @param subject (일반적으로 사용자 ID)
     * @return 생성된 JWT
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        return generateToken(claims, subject, EXPIRATION_TIME);
    }

    /**
     * JWT 토큰 생성
     *
     * @param claims    사용자에 대한 클레임 정보
     * @param subject   사용자 식별자(ID)
     * @param duration  토큰의 유효 시간 (밀리초 단위)
     * @return 생성된 JWT
     */
    public String generateToken(Map<String, Object> claims, String subject, long duration) {
        return Jwts.builder()
                .setClaims(claims) // 사용자 클레임 추가
                .setSubject(subject) // 사용자 ID 설정
                .setIssuedAt(new Date()) // 생성 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + duration)) // 동적 유효시간 설정
                .signWith(secretKey) // 서명을 위한 secret key 설정
                .compact(); // 압축 및 토큰 반환
    }

    /**
     * JWT 검증 및 클레임 추출
     * @param token JWT 토큰
     * @return 클레임 정보 반환
     */
    public Claims validateToken(String token) {
        try {
            return parseJwtToken(token);
        } catch (ExpiredJwtException ex) {
            throw new IllegalStateException("Expired JWT token");
        } catch (JwtException ex) {
            throw new IllegalStateException("Invalid JWT token");
        }
    }

    /**
     * JWT에서 사용자 식별 값 추출
     * @param token JWT 토큰
     * @return 사용자 ID (subject)
     */
    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }

    /**
     * JWT가 만료되었는지 검사
     * @param token JWT 토큰
     * @return 만료 여부 반환
     */
    public boolean isTokenExpired(String token) {
        try {
            parseJwtToken(token);
            return false;
        } catch (ExpiredJwtException ex) {
            return true;
        }
    }

    private Claims parseJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 서명 키 설정
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}