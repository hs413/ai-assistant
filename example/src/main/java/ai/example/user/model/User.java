package ai.example.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nickname")
})
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment 설정
    private Long id;

    @Column(nullable = false) // 비밀번호는 NOT NULL
    private String password;

    @Column(nullable = false, unique = true) // 이메일은 NOT NULL + UNIQUE
    private String email;

    @Column(nullable = false, unique = true) // 닉네임은 NOT NULL + UNIQUE
    private String nickname;
}