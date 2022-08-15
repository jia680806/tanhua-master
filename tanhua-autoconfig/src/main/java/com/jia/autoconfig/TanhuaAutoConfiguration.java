package com.jia.autoconfig;

import com.jia.autoconfig.template.SmsTemplate;
import org.springframework.context.annotation.Bean;

public class TanhuaAutoConfiguration {
    @Bean
    public SmsTemplate smsTemplate(){
        return new SmsTemplate();
    }
}
