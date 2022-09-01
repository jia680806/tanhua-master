package com.jia.test;

import com.jia.tanhua.AppServerApplication;
import com.jia.tanhua.autoconfig.template.SmsTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServerApplication.class)
public class SmsTemplateTest {
    @Autowired
    private SmsTemplate smsTemplate;


    @Test
    public void testSendSms(){
        smsTemplate.sendSms("17666000000","2345");
    }
}
