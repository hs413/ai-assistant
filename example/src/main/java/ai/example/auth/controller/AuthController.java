//package ai.example.auth.controller;
//
//import ai.example.auth.service.CustomUserDetailsService;
//import ai.example.jwt.JwtUtil;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService customUserDetailsService;
//
//    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
//            CustomUserDetailsService customUserDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.get("email"), loginRequest.get("password"))
//        );
//
//        String token = jwtUtil.generateToken(Map.of(), authentication.getName(), 1000 * 60 * 60); // 1시간 유효
//        return Map.of("token", token);
//    }
//}