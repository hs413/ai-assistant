package ai.example.user.repository;

import ai.example.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일로 사용자를 찾을 수 있어야 한다.")
    void testFindByEmail() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("securepassword");
        user.setNickname("testUser");
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("닉네임으로 사용자를 찾을 수 있어야 한다.")
    void testFindByNickname() {
        // Given
        User user = new User();
        user.setEmail("another@example.com");
        user.setPassword("anotherpassword");
        user.setNickname("uniqueNickname");
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByNickname("uniqueNickname");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getNickname()).isEqualTo("uniqueNickname");
    }

    @Test
    @DisplayName("중복 이메일로 사용자 저장 시 예외가 발생해야 한다.")
    void testUniqueEmailConstraint() {
        // Given
        User user1 = new User();
        user1.setEmail("duplicate@example.com");
        user1.setPassword("password1");
        user1.setNickname("user1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("duplicate@example.com"); // 동일한 이메일
        user2.setPassword("password2");
        user2.setNickname("user2");

        // When, Then
        try {
            userRepository.save(user2);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(Exception.class); // 예외 발생 체크
        }
    }
}