package ai.example.user;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String password;

    private String email;

    private String nickname;
}