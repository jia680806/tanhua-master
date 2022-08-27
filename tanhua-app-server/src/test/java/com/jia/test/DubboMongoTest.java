package com.jia.test;


import com.jia.tanhua.AppServerApplication;
import com.jia.tanhua.dubbo.api.RecommendUserApi;
import com.jia.tanhua.mongo.RecommendUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppServerApplication.class)
public class DubboMongoTest {

    @DubboReference
    private RecommendUserApi recommendUserApi;

    @Test
    public void testRecommendUser(){

        RecommendUser recommendUser = recommendUserApi.queryWithMaxScore(1L);
        System.out.println(recommendUser);

    }
}
