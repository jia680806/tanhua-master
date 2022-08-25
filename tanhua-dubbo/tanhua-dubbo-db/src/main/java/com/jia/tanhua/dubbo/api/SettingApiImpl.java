package com.jia.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jia.tanhua.domain.Settings;
import com.jia.tanhua.dubbo.mappers.SettingMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class SettingApiImpl implements SettingApi {
    @Autowired
    private SettingMapper settingMapper;
    @Override
    public Settings getSetting(Long userId) {
        LambdaQueryWrapper<Settings> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Settings::getUserId,userId);
        Settings settings = settingMapper.selectOne(queryWrapper);
        return settings;
    }
}
