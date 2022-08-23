package com.jia.autoconfig.template;


import com.baidu.aip.face.AipFace;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class AipFaceTemplate {

    @Autowired
    private AipFace client;

    public Boolean detect(String imageUrl){
        String imageType = "URL";

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");


        // 人脸检测
        JSONObject res = client.detect(imageUrl, imageType, options);
        System.out.println(res.toString(2));
         Integer errorCode  = (Integer) res.get("error_code");

         return errorCode == 0;

    }
}
