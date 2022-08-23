package com.jia.tanhua.dubbo.apiimpl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jia.tanhua.domain.User;
import com.jia.tanhua.dubbo.api.UserApi;
import com.jia.tanhua.dubbo.mappers.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


@DubboService
public class UserApiImpl implements UserApi {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getMobile,phone);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Long addUser(User user) {
        userMapper.insert(user);
        return user.getId();
    }
}
