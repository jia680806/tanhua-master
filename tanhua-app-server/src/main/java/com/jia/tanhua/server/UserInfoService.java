package com.jia.tanhua.server;

import com.jia.tanhua.autoconfig.template.AipFaceTemplate;
import com.jia.tanhua.autoconfig.template.OssTemplate;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
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
                throw new RuntimeException();
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setAvatar(headPhotoUrl);
            userInfo.setId(id);
            userInfoApi.update(userInfo);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void getUserInfo(Long id) {
        userInfoApi.findUserInfoById(id);
    }
}
