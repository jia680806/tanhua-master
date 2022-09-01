package com.jia.tanhua.dubbo.api;


import com.jia.tanhua.domain.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoApi {
    public void save(UserInfo userInfo);
    public void update(UserInfo userInfo);
    public UserInfo findUserInfoById(Long id);

    public Map<Long,UserInfo> findByIds(List<Long> id,UserInfo userInfo);

}
