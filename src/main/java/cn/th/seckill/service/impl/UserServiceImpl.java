package cn.th.seckill.service.impl;

import cn.th.seckill.entity.User;
import cn.th.seckill.mapper.UserMapper;
import cn.th.seckill.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public User selectUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public User selectUserByPhoneAndPassword(User user) {
        return userMapper.selectUserByPhoneAndPassword(user);
    }
}
