package com.jia.tanhua.autoconfig;

import com.jia.tanhua.autoconfig.properties.AipFaceProperties;
import com.jia.tanhua.autoconfig.properties.OssProperties;
import com.jia.tanhua.autoconfig.properties.SmsProperties;
import com.jia.tanhua.autoconfig.template.AipFaceTemplate;
import com.jia.tanhua.autoconfig.template.OssTemplate;
import com.jia.tanhua.autoconfig.template.SmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties({
        SmsProperties.class,
        OssProperties.class,
        AipFaceProperties.class})
public class TanhuaAutoConfiguration {
    @Bean
    public SmsTemplate smsTemplate(SmsProperties properties){
        return new SmsTemplate(properties);
    }
    @Bean public OssTemplate ossTemplate(OssProperties properties){
        return new OssTemplate(properties);
    }
    @Bean
    public AipFaceTemplate aipFaceTemplate(){
        return new AipFaceTemplate();
    }
}
