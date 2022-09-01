package com.jia.tanhua.dubbo.api;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.mappers.UserInfoMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<Long, UserInfo> findByIds(List<Long> ids, UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);

        if (userInfo != null){
            if (userInfo.getAge() !=null){
                queryWrapper.lt("age",userInfo.getAge());
            }
            if (!StringUtils.isEmpty(userInfo.getGender())){
                queryWrapper.eq("gender", userInfo.getGender());
            }
        }
        List<UserInfo> userInfos = userInfoMapper.selectList(queryWrapper);
        Map<Long, UserInfo> map = CollUtil.fieldValueMap(userInfos,"id");
        return map;

    }

}
