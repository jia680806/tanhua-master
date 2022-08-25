package com.jia.tanhua.server.controller;

import com.jia.tanhua.commons.utils.JwtUtils;

import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.server.service.UserInfoService;
import com.jia.tanhua.vo.SettingsVo;
import com.jia.tanhua.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity getUserinfo(Long userId){

        if (userId == null)
            userId = BaseContext.getUserId();

        UserInfoVo userInfovo =  userInfoService.getUserInfo(userId);

        return ResponseEntity.ok(userInfovo);

    }

    @PutMapping
    public ResponseEntity updateUserinfo(@RequestBody UserInfo userInfo){

        Long userId = BaseContext.getUserId();

        userInfo.setId(userId);
        userInfoService.updateUserInfo(userInfo);

        return ResponseEntity.ok(null);

    }
    @PostMapping("/header")
    public ResponseEntity updateHead(MultipartFile headPhoto){

        Long userId = BaseContext.getUserId();

        userInfoService.updateHead(headPhoto,userId);

        return ResponseEntity.ok(null);

    }

    @GetMapping( "/settings")
    public ResponseEntity getSetting(){
        Long userId = BaseContext.getUserId();
        SettingsVo settingVo = userInfoService.getSettingVo(userId);
        return ResponseEntity.ok(settingVo);
    }

    @PostMapping("/questions")
    public ResponseEntity setQuestions(@RequestBody String content){
        userInfoService.addQuestion(content);
        return ResponseEntity.ok(null);
    }

}
