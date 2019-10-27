package capstone.placer.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> get() {
        return userMapper.get();
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

    public boolean login(String mail, String password) {

        // mail validation
        if (userMapper.getMatchMailCount(mail) != 1)
            return false;

        String encryptedPassword = userMapper.getEncryptedPassword(mail);
        return passwordEncoder.matches(password, encryptedPassword);
    }
}
