package ai.example.auth.service;

import ai.example.auth.dto.LoginRequest;
import ai.example.auth.dto.LoginResponse;
import ai.example.jwt.JwtUtil;
import ai.example.user.model.User;
import ai.example.user.repository.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest loginRequestDto) {
        // 이메일과 비밀번호 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        // 인증 성공 후 JWT 발급
        String token = jwtUtil.generateToken(Map.of(), loginRequestDto.getEmail());

        // 사용자 정보를 검증 후 반환
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new LoginResponse(token);
    }
}