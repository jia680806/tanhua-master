package com.jia.tanhua.dubbo.apiimpl;


import com.jia.tanhua.dubbo.api.UserApi;
import com.jia.tanhua.dubbo.mappers.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


@DubboService
public class UserApiImpl implements UserApi {
    @Autowired
    private UserMapper userMapper;

}
