package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.RecommendUser;
import com.jia.tanhua.vo.PageResult;
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

    @Override
    public PageResult getRecommendPage(Integer page, Integer pagesize, Long userId) {
        Criteria criteria = Criteria.where("toUserId").is(userId);
        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query,RecommendUser.class);
        query.with(Sort.by(Sort.Order.desc("score"))).limit((page-1)*pagesize).skip(pagesize);


        List<RecommendUser> recommendUsers = mongoTemplate.find(query, RecommendUser.class);
        PageResult pageResult = new PageResult(page,pagesize,(int) count,recommendUsers);

        return pageResult;
    }

}
