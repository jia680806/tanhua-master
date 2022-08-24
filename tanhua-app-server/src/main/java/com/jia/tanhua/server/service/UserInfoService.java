package com.jia.tanhua.server.service;

import com.jia.tanhua.autoconfig.template.AipFaceTemplate;
import com.jia.tanhua.autoconfig.template.OssTemplate;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.vo.ErrorResult;
import com.jia.tanhua.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class UserInfoService {
    @DubboReference
    private UserInfoApi userInfoApi;

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
}
