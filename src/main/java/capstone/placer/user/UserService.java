package capstone.placer.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> get(long id) {
        return userMapper.get(id);
    }

    public boolean register(String nickname, String mail, String password) {

        // duplicated mail check
        if (userMapper.getMatchMailCount(mail) > 0)
            return false;

        // encode password
        String encrpytedPassword = passwordEncoder.encode(password);

        User user = new User(nickname, mail, encrpytedPassword);

        userMapper.insertNewUser(user);
        return true;
    }

    public User login(String mail, String password) {

        // mail validation
        if (userMapper.getMatchMailCount(mail) != 1)
            return null;

        String encryptedPassword = userMapper.getEncryptedPassword(mail);
        if (passwordEncoder.matches(password, encryptedPassword)) {
            return userMapper.getByMail(mail);
        } else return null;
    }
}
