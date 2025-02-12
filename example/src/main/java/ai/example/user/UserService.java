package ai.example.user;

import ai.example.user.model.User;
import ai.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 사용자 등록 구현
     * @param userRequestDto 사용자 정보
     */
    public void registerUser(UserRequestDto userRequestDto) {
        // 이메일 중복 및 닉네임 중복 검사
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (userRepository.findByNickname(userRequestDto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("Nickname is already in use");
        }

        // 엔티티로 변환 후 패스워드 암호화
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setNickname(userRequestDto.getNickname());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        // 사용자 저장
        userRepository.save(user);
    }
}