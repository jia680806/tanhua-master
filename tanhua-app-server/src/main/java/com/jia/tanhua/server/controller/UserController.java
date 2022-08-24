package com.jia.tanhua.server.controller;

import com.jia.tanhua.commons.utils.JwtUtils;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.server.service.UserInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 保存用户信息
     * @param userInfo 用户填写的信息
     * @param token
     * @return
     */

    @PostMapping("/loginReginfo")
    public ResponseEntity loginReginfo(@RequestBody UserInfo userInfo){


        //提取id信息
//        Claims claims = JwtUtils.getClaims(token);
//        Integer id = (Integer)claims.get("id");
        Long userId = BaseContext.getUserId();
        //设置并保存id
        userInfo.setId(userId);
        userInfoService.save(userInfo);

        return ResponseEntity.ok(null);
    }
    @PostMapping("loginReginfo/head")
    public ResponseEntity setHead(MultipartFile headPhoto){


        Long userInfoId  = BaseContext.getUserId();

        userInfoService.updateHead(headPhoto,userInfoId);


        return ResponseEntity.ok(null);
    }
}
