package com.jia.tanhua.server.service;

import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.RecommendUserApi;
import com.jia.tanhua.mongo.RecommendUser;
import com.jia.tanhua.vo.TodayBest;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TanhuaService {

    @DubboReference
    private RecommendUserApi recommendUserApi;
    @Autowired
    private UserInfoService userInfoService;

    public TodayBest todayBest(Long userId) {

        RecommendUser recommendUser = recommendUserApi.queryWithMaxScore(userId);
        if (recommendUser ==null){
            recommendUser =new RecommendUser();
            recommendUser.setUserId(1L);
            recommendUser.setScore(99d);
        }
        Long getUserId = recommendUser.getUserId();

        UserInfo userInfo = userInfoService.getUserInfo(getUserId);
        TodayBest todayBest = TodayBest.init(userInfo, recommendUser);

        return todayBest;


    }
}
