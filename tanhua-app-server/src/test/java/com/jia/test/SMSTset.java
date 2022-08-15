package com.jia.test;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.*;
import com.aliyun.teautil.models.*;
public class SMSTset {
   

    public static void main(String[] args_) throws Exception {
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
                    .setPhoneNumbers("17666002200")
                    .setTemplateParam("{\"code\":\"1234\"}");


            SendSmsResponse response = client.sendSms(sendSmsRequest);
            SendSmsResponseBody body = response.getBody();
            System.out.println(body.message);
        }catch (Exception e){
            System.out.println(e);}
    }
}
