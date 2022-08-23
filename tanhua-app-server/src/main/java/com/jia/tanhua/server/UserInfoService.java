package com.jia.tanhua.server;

import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInfoService {
    @DubboReference
    private UserInfoApi userInfoApi;

    public void save(UserInfo userInfo){
        userInfoApi.save(userInfo);
    }

}
