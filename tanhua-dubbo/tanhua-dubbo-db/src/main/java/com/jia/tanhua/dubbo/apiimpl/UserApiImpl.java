package com.jia.tanhua.dubbo.apiimpl;


import com.jia.tanhua.dubbo.api.UserApi;
import com.jia.tanhua.dubbo.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;



public class UserApiImpl implements UserApi {
    @Autowired
    private UserMapper userMapper;

}
