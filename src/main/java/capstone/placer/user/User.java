package capstone.placer.user;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;

@Data
public class User {

    public User(String nickname, String mail, String password) {
        this.nickname = nickname;
        this.mail = mail;
        this.password = password;
    }

    @NonNull
    private long id;

    @NonNull
    private String nickname;

    @NonNull
    @Email
    private String mail;

    @NonNull
    private String password;
}
