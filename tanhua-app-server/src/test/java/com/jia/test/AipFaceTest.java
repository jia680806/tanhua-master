package com.jia.test;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

public class AipFaceTest {
        //设置APPID/AK/SK
        public static final String APP_ID = "27118630";
        public static final String API_KEY = "O4GoRa2Sz1342eTubzTyB1W0";
        public static final String SECRET_KEY = "Ag1eHjBQnC0GMV7Gd1MyGeR5QR4gnHG9";
        @Test
        public  void testface() {
            // 初始化一个AipFace
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            HashMap<String, String> options = new HashMap<String, String>();
            options.put("face_field", "age");
            options.put("max_face_num", "2");
            options.put("face_type", "LIVE");
            options.put("liveness_control", "LOW");



            // 调用接口
            String image = "https://tanhua-master.oss-cn-heyuan.aliyuncs.com/2022/08/22/3e462572-9707-4dc3-b5eb-9a2ecca3e97e.jpg";
            String imageType = "URL";

            // 人脸检测
            JSONObject res = client.detect(image, imageType, options);
            System.out.println(res.toString(2));

        }
    }
