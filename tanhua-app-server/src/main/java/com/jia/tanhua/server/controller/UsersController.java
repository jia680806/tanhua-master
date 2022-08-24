package com.jia.tanhua.server.controller;

import com.jia.tanhua.commons.utils.JwtUtils;

import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.server.UserInfoService;
import com.jia.tanhua.vo.UserInfoVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
        if( !JwtUtils.verifyToken(token)){
            throw new RuntimeException();
        }

        Claims claims = JwtUtils.getClaims(token);
        Long id = (Long) claims.get("id");

        userInfo.setId(id);
        userInfoService.updateUserInfo(userInfo);

        return ResponseEntity.ok(null);

    }
}
