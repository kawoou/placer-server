package capstone.placer.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    final private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
