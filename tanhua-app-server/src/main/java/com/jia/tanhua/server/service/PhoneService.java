package com.jia.tanhua.server.service;

import com.jia.tanhua.autoconfig.template.SmsTemplate;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.server.interceptor.BaseContext;

import com.jia.tanhua.vo.ErrorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {


    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    public void sendVerificationCode() {
        //1.获得自身的手机号码
        String userPhone = BaseContext.getUserPhone();
        //2.生成随机的验证码
//        String code = RandomStringUtils.randomNumeric(6);
        String code = "123456";
        //3.发送验证码
        smsTemplate.sendSms(userPhone,code);
        //4.存到redis
        redisTemplate.opsForValue().set("REVISE"+userPhone,code);
    }

    public boolean checkVerificationCode(String verificationCode) {
        String userPhone = BaseContext.getUserPhone();
        if(verificationCode == null || redisTemplate.opsForValue().get("REVISE"+userPhone) != verificationCode){
            return false;
        }
        return true;
    }
}
