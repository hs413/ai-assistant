//package ai.example.auth.service;
//
//import ai.example.auth.dto.LoginRequest;
//import ai.example.auth.dto.LoginResponse;
//import ai.example.jwt.JwtUtil;
//import ai.example.user.model.User;
//import ai.example.user.repository.UserRepository;
//import java.util.Map;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
//    public LoginResponse login(LoginRequest request) {
//        // 이메일로 사용자 조회
//        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
//        if (userOptional.isEmpty()) {
//            throw new IllegalArgumentException("Invalid email or password"); // 401 에러 처리
//        }
//
//        User user = userOptional.get();
//
//        // 비밀번호 확인
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Invalid email or password"); // 401 에러 처리
//        }
//
//        // JWT 생성 및 반환
//        String token = jwtUtil.generateToken(
//                Map.of("role", "USER"), // 사용자 역할 정보를 클레임에 추가
//                user.getEmail(),       // subject에 이메일 설정
//                1000 * 60 * 60         // 토큰 유효기간: 1시간
//        );
//
//        return new LoginResponse(token);
//    }
//}