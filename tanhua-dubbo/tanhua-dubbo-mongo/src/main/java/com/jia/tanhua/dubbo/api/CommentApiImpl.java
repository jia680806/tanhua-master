package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.enums.CommentType;
import com.jia.tanhua.mongo.Comment;
import com.jia.tanhua.mongo.Movement;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@DubboService
public class CommentApiImpl implements CommentApi{

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public Integer save(Comment comment) {
        //查询动态
        ObjectId publishId = comment.getPublishId();
//        Movement movement = mongoTemplate.findOne(query, Movement.class);
        Movement movement = mongoTemplate.findById(publishId, Movement.class);
        if (movement != null){
            comment.setPublishUserId(movement.getUserId());
        }

        //评论数加1
        //设置查询条件
        Criteria criteria = Criteria.where("id").is(publishId);
        Query query = Query.query(criteria);
        //设置更新条件
        Update update = new Update();
        if (comment.getCommentType() == CommentType.COMMENT.getType()){
            update.inc("commentCount",1);
        }
        if (comment.getCommentType() == CommentType.LOVE.getType()){
            update.inc("loveCount",1);
        }
        if (comment.getCommentType() ==CommentType.LIKE.getType()){
            update.inc("likeCount",1);
        }
        //更新设置参数
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        Movement modify = mongoTemplate.findAndModify(query, update, options, Movement.class);


        return modify.statisCount(comment.getCommentType());

    }
}
