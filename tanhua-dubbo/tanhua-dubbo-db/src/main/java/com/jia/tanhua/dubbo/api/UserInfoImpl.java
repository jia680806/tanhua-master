package com.jia.tanhua.dubbo.api;


import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.mappers.UserInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class UserInfoImpl implements UserInfoApi {


    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public void save(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);

    }

    @Override
    public void update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);

    }

    @Override
    public UserInfo findUserInfoById(Long id) {
        return userInfoMapper.selectById(id);

    }

}
