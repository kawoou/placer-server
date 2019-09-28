package capstone.placer.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int register(User user) throws Exception;
}
