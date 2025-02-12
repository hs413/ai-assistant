package ai.example.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ai.example.user.UserService userService;

    /**
     * 사용자 등록
     * @param userRequestDto 사용자 정보
     * @return 등록된 사용자 응답
     */
    @PostMapping
    public ResponseEntity<String> registerUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}