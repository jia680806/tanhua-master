package com.jia.tanhua.server;

import com.jia.tanhua.autoconfig.template.SmsTemplate;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.Map;

public class UserService {

    @Autowired
    private SmsTemplate template;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void sendMsg(String phone) {
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        template.sendSms(phone,code);
        redisTemplate.opsForValue().set("CODE_"+phone,code);
    }




}
