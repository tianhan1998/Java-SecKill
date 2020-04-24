package cn.th.seckill.mapper;

import cn.th.seckill.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insertUser(User user);
    User selectUserByPhone(String phone);
    User selectUserByPhoneAndPassword(User user);
}
