package com.jia.tanhua.server.service;

import com.jia.tanhua.autoconfig.template.AipFaceTemplate;
import com.jia.tanhua.autoconfig.template.OssTemplate;
import com.jia.tanhua.domain.Question;
import com.jia.tanhua.domain.Settings;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.QuestionApi;
import com.jia.tanhua.dubbo.api.SettingsApi;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.vo.ErrorResult;
import com.jia.tanhua.vo.SettingsVo;
import com.jia.tanhua.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class UserInfoService {
    @DubboReference
    private UserInfoApi userInfoApi;
    @DubboReference
    private QuestionApi questionApi;
    @DubboReference
    private SettingsApi settingsApi;

    @Autowired
    private AipFaceTemplate faceTemplate;

    @Autowired
    private OssTemplate ossTemplate;


    public void save(UserInfo userInfo){
        userInfoApi.save(userInfo);
    }

    public void updateHead(MultipartFile headPhoto, Long id ){

        try {
            //1.阿里云上传图片
            String headPhotoUrl = ossTemplate.upload(headPhoto.getOriginalFilename(), headPhoto.getInputStream());

            //2.百度识图识别照片
            Boolean isFace = faceTemplate.detect(headPhotoUrl);

            if (!isFace){
                throw new BusinessException(ErrorResult.faceError());
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setAvatar(headPhotoUrl);
            userInfo.setId(id);
            userInfoApi.update(userInfo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public UserInfoVo getUserInfo(Long id) {
        UserInfo userInfo = userInfoApi.findUserInfoById(id);
        UserInfoVo userInfoVo = new UserInfoVo();

        BeanUtils.copyProperties(userInfo,userInfoVo);
        String age =  String.valueOf(userInfo.getAge());

        userInfoVo.setAge(age);

        return userInfoVo;


    }

    public void updateUserInfo(UserInfo userInfo) {
        userInfoApi.update(userInfo);
    }

    public SettingsVo getSettingVo(Long userId) {

        SettingsVo settingsVo = new SettingsVo();

        //1.获取用户id
        Long id= BaseContext.getUserId();
        settingsVo.setId(id);

        //2.获取用户手机
        String phone =BaseContext.getUserPhone();
        settingsVo.setPhone(phone);

        //3.获取用户陌生人问题
        Question question = questionApi.findQuestionByUserId(userId);
        if (question==null)
            settingsVo.setStrangerQuestion("该用户还没有想好要问什么，试下打声招呼吧");
        else settingsVo.setStrangerQuestion(question.getTxt());

        //4.获取用户app开关
        Settings setting = settingsApi.findSettingByUserId(userId);
        if (setting != null)
            BeanUtils.copyProperties(setting,settingsVo);


        return settingsVo;


    }

    public void editQuestion(String content) {


        Question question = questionApi.findQuestionByUserId(BaseContext.getUserId());


      if (question == null) {
          question = new Question();
          question.setTxt(content);
          question.setUserId(BaseContext.getUserId());
          questionApi.saveQuestion(question);
      }else {
            question.setTxt(content);
            questionApi.updateQuestion(question);
      }
    }

    public void editSettings(Map settingsMap) {

        boolean likeNotification = (boolean) settingsMap.get("likeNotification");
        boolean pinglunNotification = (boolean) settingsMap.get("pinglunNotification");
        boolean gonggaoNotification = (boolean) settingsMap.get("gonggaoNotification");
        Settings settings = new Settings();
        settings.setGonggaoNotification(gonggaoNotification);
        settings.setPinglunNotification(pinglunNotification);
        settings.setLikeNotification(likeNotification);
        settings.setUserId(BaseContext.getUserId());

        settingsApi.update(settings);
    }
}
