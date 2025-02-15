package ai.example.auth.controller;


import ai.example.auth.dto.LoginRequest;
import ai.example.auth.dto.LoginResponse;
import ai.example.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequestDto) {
        LoginResponse response = authService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}