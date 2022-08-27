package com.jia.tanhua.server.service;

import com.jia.tanhua.autoconfig.template.SmsTemplate;
import com.jia.tanhua.domain.User;
import com.jia.tanhua.dubbo.api.UserApi;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.server.interceptor.BaseContext;

import com.jia.tanhua.vo.ErrorResult;
import lombok.extern.log4j.Log4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Log4j
public class PhoneService {


    @Autowired
    private SmsTemplate smsTemplate;

    @DubboReference
    private UserApi userApi;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    public void sendVerificationCode() {
        //1.获得自身的手机号码
        String userPhone = BaseContext.getUserPhone();
        //2.生成随机的验证码
//        String code = RandomStringUtils.randomNumeric(6);
        String code = "123456";
        //3.发送验证码
//        smsTemplate.sendSms(userPhone,code);
        //4.存到redis
        redisTemplate.opsForValue().set("REVISE"+userPhone,code, Duration.ofMinutes(50));
    }

    public boolean checkVerificationCode(String verificationCode) {
        String userPhone = BaseContext.getUserPhone();
        String redisCode = redisTemplate.opsForValue().get("REVISE"+userPhone);
        if(verificationCode == null || !redisCode.equals(verificationCode)){
            return false;
        }
        return true;
    }

    public void setNewPhone(String phone) {
        //1.先检查该电话号码是否有其他用户使用
        User user = userApi.findUserByPhone(phone);
        if (user != null){
            throw new BusinessException(ErrorResult.mobileError());
        }
        //再检查电话号码是否符合标准
        if (phone.length() != 11){
            throw new BusinessException(ErrorResult.mobileLengthError());
        }

        //设置新电话
        User thisUser = userApi.findUserByPhone(BaseContext.getUserPhone());
        thisUser.setMobile(phone);
        userApi.updatePhone(thisUser);
    }
}
