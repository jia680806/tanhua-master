package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.RecommendUser;

public interface RecommendUserApi {
    public RecommendUser queryWithMaxScore(Long toUserId);

}
