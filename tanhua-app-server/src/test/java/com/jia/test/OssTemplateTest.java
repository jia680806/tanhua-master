package com.jia.test;

import com.jia.tanhua.AppServerApplication;
import com.jia.tanhua.autoconfig.template.OssTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServerApplication.class)
public class OssTemplateTest {

    @Autowired
    private OssTemplate ossTemplate;
    @Test
    public void testOssTemplate() throws FileNotFoundException {
        String path = "H:\\img\\0.jpg";
        FileInputStream inputStream = new FileInputStream(new File(path));
        String url = ossTemplate.upload(path, inputStream);
        System.out.println(url);
    }
}
