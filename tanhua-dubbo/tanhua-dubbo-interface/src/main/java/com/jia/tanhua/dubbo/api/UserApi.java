package com.jia.tanhua.dubbo.api;


import com.jia.tanhua.domain.User;

public interface UserApi {
    User findUserByPhone(String phone);

    Long addUser(User user);

    void updatePhone(User user);
}
