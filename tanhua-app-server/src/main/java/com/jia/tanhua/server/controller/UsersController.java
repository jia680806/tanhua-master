package com.jia.tanhua.server.controller;

import com.jia.tanhua.commons.utils.JwtUtils;

import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.server.service.UserInfoService;
import com.jia.tanhua.vo.ErrorResult;
import com.jia.tanhua.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
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
    public ResponseEntity getUserinfo(Long userId,
                                      @RequestHeader("Authorization") String token){
        if (!JwtUtils.verifyToken(token)){
            throw new RuntimeException("token失效");
        }

        if (userId == null){
            Claims claims = JwtUtils.getClaims(token);
            Integer id = (Integer) claims.get("id");
            userId = Long.valueOf(id);
        }

        UserInfoVo userInfovo =  userInfoService.getUserInfo(userId);

        return ResponseEntity.ok(userInfovo);

    }

    @PutMapping
    public ResponseEntity updateUserinfo(@RequestBody UserInfo userInfo,
                                      @RequestHeader("Authorization") String token){

        Long userId = BaseContext.getUserId();

        userInfo.setId(userId);
        userInfoService.updateUserInfo(userInfo);

        return ResponseEntity.ok(null);

    }
    @PostMapping("/header")
    public ResponseEntity updateHead(@RequestHeader("Authorization") String token,
                                     MultipartFile headPhoto){

        Long userId = BaseContext.getUserId();

        userInfoService.updateHead(headPhoto,userId);

        return ResponseEntity.ok(null);

    }
}
