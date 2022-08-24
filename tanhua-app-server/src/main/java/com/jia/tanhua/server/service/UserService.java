package com.jia.tanhua.server.service;

import com.jia.tanhua.autoconfig.template.SmsTemplate;

import com.jia.tanhua.commons.utils.JwtUtils;
import com.jia.tanhua.domain.User;
import com.jia.tanhua.dubbo.api.UserApi;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.vo.ErrorResult;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j
public class UserService {

    @Autowired
    private SmsTemplate template;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @DubboReference
    private UserApi userApi;

    public void sendMsg(String phone) {
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        template.sendSms(phone,code);
        redisTemplate.opsForValue().set("CODE_"+phone,code, Duration.ofMinutes(50));
    }


    public Map loginVerification(String phone, String code) {
        String redisCode = (String) redisTemplate.opsForValue().get("CODE_"+phone);
        if (StringUtils.isEmpty(redisCode) || !redisCode.equals(code)){
            throw new BusinessException(ErrorResult.loginError());
        }

        Boolean isNew = false;
        User user = userApi.findUserByPhone(phone);
        if (user==null){
            user = new User();
            user.setMobile(phone);
            user.setPassword(DigestUtils.md5Hex("123456"));
            Long userId = userApi.addUser(user);
            user.setId(userId);
            isNew = true;
        }

        Map tokenMap = new HashMap();
        tokenMap.put("id",user.getId());
        tokenMap.put("mobile",user.getMobile());

        String token = JwtUtils.getToken(tokenMap);

        Map resMap = new HashMap<>();
        resMap.put("token",token);
        resMap.put("isNew",isNew);
        return resMap;

    }
}
