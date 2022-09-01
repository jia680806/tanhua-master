package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.RecommendUser;
import com.jia.tanhua.vo.PageResult;

public interface RecommendUserApi {
    public RecommendUser queryWithMaxScore(Long toUserId);

    PageResult getRecommendPage(Integer page, Integer pagesize, Long userId);
}
