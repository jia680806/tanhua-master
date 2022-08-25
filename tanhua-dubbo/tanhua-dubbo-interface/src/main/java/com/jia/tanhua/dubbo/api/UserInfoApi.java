package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.domain.Question;
import com.jia.tanhua.domain.Settings;
import com.jia.tanhua.domain.UserInfo;

public interface UserInfoApi {
    public void save(UserInfo userInfo);
    public void update(UserInfo userInfo);
    public UserInfo findUserInfoById(Long id);
    public Settings findSettingById(Long userId);
    public Question findQuestionById(Long userId);
}
