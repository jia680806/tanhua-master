package com.jia.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jia.tanhua.domain.UserInfo;

public interface BlackListApi {

    IPage<UserInfo> findByUserId(Long userId, int page, int size);

    void deleteBlackUser(Long userId, Long uid);
}
