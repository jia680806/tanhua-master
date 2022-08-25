package com.jia.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jia.tanhua.domain.Question;
import com.jia.tanhua.domain.Settings;
import com.jia.tanhua.dubbo.mappers.SettingsMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class SettingsApiImpl implements SettingsApi {
    @Autowired
    private SettingsMapper settingMapper;

    @Override
    public void update(Settings settings) {
        LambdaQueryWrapper<Settings> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Settings::getUserId,settings.getUserId());
        settingMapper.update(settings,queryWrapper);
    }

    @Override
    public Settings findSettingByUserId(Long userId) {
        LambdaQueryWrapper<Settings> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Settings::getUserId,userId);
        Settings settings = settingMapper.selectOne(queryWrapper);
        return settings;
    }
}
