package com.jia.tanhua.server.controller;

import com.jia.tanhua.commons.utils.JwtUtils;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.server.UserInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    public ResponseEntity loginReginfo(@RequestBody UserInfo userInfo,
                                       @RequestHeader("Authorization")String token){

        //判断token是否合法
        if(token == null || !JwtUtils.verifyToken(token)){
            return ResponseEntity.status(401).body(null);
        }

        //提取id信息
        Claims claims = JwtUtils.getClaims(token);
        Integer id = (Integer)claims.get("id");
        //设置并保存id
        userInfo.setId(Long.valueOf(id));
        userInfoService.save(userInfo);


        return ResponseEntity.ok(null);
    }
}
