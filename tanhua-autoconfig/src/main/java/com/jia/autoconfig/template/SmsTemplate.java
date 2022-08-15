package com.jia.autoconfig.template;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.jia.autoconfig.properties.SmsProperties;


public class SmsTemplate {

    private SmsProperties smsProperties;


    public SmsTemplate (SmsProperties properties){
        this.smsProperties = properties;
    }
    public void sendSms(String mobile,String code)  {


        try {
            Config config = new Config()
                    // 您的 AccessKey ID
                    .setAccessKeyId(smsProperties.getAccessKeyId())
                    // 您的 AccessKey Secret
                    .setAccessKeySecret(smsProperties.getAccessKeySecret());
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";

            Client client = new Client(config);

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(smsProperties.getSignName())
                    .setTemplateCode(smsProperties.getTemplateCode())
                    .setPhoneNumbers(mobile)
                    .setTemplateParam("{\"code\":\""+code+"\"}");


            SendSmsResponse response = client.sendSms(sendSmsRequest);
            SendSmsResponseBody body = response.getBody();
            System.out.println(body.message);
        }catch (Exception e){
            System.out.println(e);}
    }

}
