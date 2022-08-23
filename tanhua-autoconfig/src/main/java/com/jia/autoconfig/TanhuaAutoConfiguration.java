package com.jia.autoconfig;

import com.baidu.aip.face.AipFace;
import com.jia.autoconfig.properties.AipFaceProperties;
import com.jia.autoconfig.properties.OssProperties;
import com.jia.autoconfig.properties.SmsProperties;
import com.jia.autoconfig.template.AipFaceTemplate;
import com.jia.autoconfig.template.OssTemplate;
import com.jia.autoconfig.template.SmsTemplate;
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
