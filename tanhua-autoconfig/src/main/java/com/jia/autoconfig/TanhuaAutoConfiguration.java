package com.jia.autoconfig;

import com.jia.autoconfig.properties.SmsProperties;
import com.jia.autoconfig.template.SmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(SmsProperties.class)
public class TanhuaAutoConfiguration {
    @Bean
    public SmsTemplate smsTemplate(SmsProperties properties){
        return new SmsTemplate(properties);
    }
}
