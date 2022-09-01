package com.jia.test;

import com.jia.tanhua.AppServerApplication;
import com.jia.tanhua.autoconfig.template.SmsTemplate;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServerApplication.class)
public class UserInfoTest {
    @DubboReference
    private UserInfoApi userInfoApi;


    @Test
    public void test(){
        List ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        ids.add(5L);
        UserInfo userInfo = new UserInfo();
        userInfo.setAge(23);

        Map map =  userInfoApi.findByIds(ids,userInfo);
        map.forEach((k,v) -> System.out.println(k+"------"+v));
    }
}
