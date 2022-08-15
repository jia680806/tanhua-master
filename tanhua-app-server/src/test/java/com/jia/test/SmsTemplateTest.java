package com.jia.test;

import com.jia.autoconfig.template.SmsTemplate;
import com.jia.server.AppServerApplication;
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
        smsTemplate.sendSms("17666002208","2345");
    }
}
