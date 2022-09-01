package com.jia.tanhua.server.service;

import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dto.RecommendUserDto;
import com.jia.tanhua.dubbo.api.RecommendUserApi;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import com.jia.tanhua.mongo.RecommendUser;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.vo.PageResult;
import com.jia.tanhua.vo.TodayBest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TanhuaService {

    @DubboReference
    private RecommendUserApi recommendUserApi;
    @DubboReference
    private UserInfoApi userInfoApi;
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

    //查询推荐好友列表
    public PageResult recommendation(RecommendUserDto dto) {
        Long userId = BaseContext.getUserId();
        //2.调用recommendationApi分页查询数据列表
        PageResult pr = recommendUserApi.getRecommendPage(dto.getPage(),dto.getPagesize(),userId);

        //3.获取分页中的列表数据
        List<RecommendUser> items = (List<RecommendUser>) pr.getItems();
        //4.判断是否为空
        if (items == null){
            return pr;
        }
        //5.循环数据列表， 查询对应的用户id信息
        List<TodayBest> todayBests = new ArrayList<>();
        for (RecommendUser item : items) {
            UserInfo userInfo = userInfoApi.findUserInfoById(item.getUserId());
            if (userInfo!=null){
                if (!StringUtils.isEmpty(dto.getGender())&& !userInfo.getGender().equals(dto.getGender())){
                    continue;
                }
                if (dto.getAge() != null && dto.getAge() < userInfo.getAge()){
                    continue;
                }
                TodayBest vo = TodayBest.init(userInfo,item);
                todayBests.add(vo);
            }
        }


        //6.构造返回值
        pr.setItems(todayBests);
        return pr;



    }
}
