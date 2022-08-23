package com.jia.test;


import com.jia.tanhua.autoconfig.template.AipFaceTemplate;
import com.jia.server.AppServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServerApplication.class)
public class AipFaceTemplateTest {
    @Autowired
    private AipFaceTemplate template;
    @Test
    public void faceTest(){

    String image = "https://tanhua001.oss-cn-beijing.aliyuncs.com/2021/04/19/a3824a45-70e3-4655-8106-a1e1be009a5e.jpg";
    boolean detect = template.detect(image);
    System.out.println(detect);
    }
}

