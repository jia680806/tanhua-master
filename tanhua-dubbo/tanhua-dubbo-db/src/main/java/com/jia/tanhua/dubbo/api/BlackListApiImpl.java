package com.jia.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jia.tanhua.domain.BlackList;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.mappers.BlackListMapper;
import com.jia.tanhua.dubbo.mappers.UserInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class BlackListApiImpl implements BlackListApi{

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private BlackListMapper blackListMapper;
    @Override
    public IPage<UserInfo> findByUserId(Long userId, int page, int size) {
        //构建分页参数对象
        Page pages = new Page(page,size);
        return userInfoMapper.findBlackList(pages,userId);

    }

    @Override
    public void deleteBlackUser(Long userId, Long uid) {
        LambdaQueryWrapper<BlackList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BlackList::getBlackUserId,uid).eq(BlackList::getUserId,userId);
        blackListMapper.selectOne(queryWrapper);
    }
}
