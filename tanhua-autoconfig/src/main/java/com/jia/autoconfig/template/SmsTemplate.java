package com.jia.autoconfig.template;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;


public class SmsTemplate {
    public void sendSms(String mobile,String code)  {

        String accessKeyId ="";
        String accessKeySecret ="";

        try {
            Config config = new Config()
                    // 您的 AccessKey ID
                    .setAccessKeyId(accessKeyId)
                    // 您的 AccessKey Secret
                    .setAccessKeySecret(accessKeySecret);
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";

            Client client = new Client(config);

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers(mobile)
                    .setTemplateParam("{\"code\":\""+code+"\"}");


            SendSmsResponse response = client.sendSms(sendSmsRequest);
            SendSmsResponseBody body = response.getBody();
            System.out.println(body.message);
        }catch (Exception e){
            System.out.println(e);}
    }

}
