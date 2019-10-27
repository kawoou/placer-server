package capstone.placer.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> get(long id);

    User getByMail(String mail);

    int getMatchMailCount(String mail);

    String getEncryptedPassword(String mail);

    void insertNewUser(User user);
}
