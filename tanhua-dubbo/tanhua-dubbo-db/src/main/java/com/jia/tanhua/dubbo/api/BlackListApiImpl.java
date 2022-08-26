package com.jia.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.mappers.UserInfoMapper;
import com.jia.tanhua.vo.PageResult;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class BlackListApiImpl implements BlackListApi{

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public IPage<UserInfo> findByUserId(Long userId, int page, int size) {
        //构建分页参数对象
        Page pages = new Page(page,size);
        return userInfoMapper.findBlackList(pages,userId);

    }
}
