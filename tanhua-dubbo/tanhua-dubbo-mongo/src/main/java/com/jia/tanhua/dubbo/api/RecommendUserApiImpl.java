package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.RecommendUser;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@DubboService
public class RecommendUserApiImpl implements RecommendUserApi {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public RecommendUser queryWithMaxScore(Long toUserId) {
        Criteria criteria = Criteria.where("toUserId").is(toUserId);

        Query query = Query.query(criteria);
        query.with(Sort.by(Sort.Order.desc("score")));
        List<RecommendUser> recommendUsers = mongoTemplate.find(query, RecommendUser.class);
        return recommendUsers.get(0);
    }
}
