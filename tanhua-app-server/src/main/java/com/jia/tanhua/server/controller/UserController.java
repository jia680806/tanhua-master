package com.jia.tanhua.server.controller;

import com.jia.tanhua.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

public class UserController{


    @Autowired
    private UserService userService;

    @RestController
    @RequestMapping("/user")
    public class LoginController {

        @Autowired
        private UserService userService;

        /**
         * 获取登录验证码
         *   请求参数：phone （Map）
         *   响应：void
         */
        @PostMapping("/login")
        public ResponseEntity login(@RequestBody Map map){
            String phone =(String) map.get("phone");
            userService.sendMsg(phone);
            return ResponseEntity.ok(null); //正常返回状态码200
        }
    }
    /**
     }
 * 检验登录
 */

}