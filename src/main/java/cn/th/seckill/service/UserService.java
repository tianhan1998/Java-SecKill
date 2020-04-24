package cn.th.seckill.service;

import cn.th.seckill.entity.User;

public interface UserService {
    int insertUser(User user);
    User selectUserByPhone(String phone);
    User selectUserByPhoneAndPassword(User user);
}
