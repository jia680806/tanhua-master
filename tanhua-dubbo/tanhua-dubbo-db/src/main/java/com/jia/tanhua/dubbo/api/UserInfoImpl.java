package com.jia.tanhua.dubbo.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jia.tanhua.domain.Question;
import com.jia.tanhua.domain.Settings;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.mappers.QuestionMapper;
import com.jia.tanhua.dubbo.mappers.SettingMapper;
import com.jia.tanhua.dubbo.mappers.UserInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class UserInfoImpl implements UserInfoApi {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private SettingMapper settingMapper;
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
    public Settings findSettingById(Long userId) {
        LambdaQueryWrapper<Settings> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Settings::getUserId,userId);
        Settings settings = settingMapper.selectOne(queryWrapper);
        return settings;
    }

    @Override
    public Question findQuestionById(Long userId) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Question::getUserId,userId);
        Question question = questionMapper.selectOne(queryWrapper);
        return question;


    }
}
