package com.jia.tanhua.server.service;

import com.jia.tanhua.dubbo.api.CommentApi;
import com.jia.tanhua.enums.CommentType;
import com.jia.tanhua.mongo.Comment;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.vo.PageResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @DubboReference
    private CommentApi commentApi;

    public void saveComments(String movementId, String comment) {
        Long userId = BaseContext.getUserId();
        Comment comment1 = new Comment();
        comment1.setPublishId(new ObjectId(movementId));
        comment1.setCommentType(CommentType.COMMENT.getType());
        comment1.setContent(comment);
        comment1.setUserId(userId);
        comment1.setCreated(System.currentTimeMillis());
        commentApi.save(comment1);

    }

    public PageResult findFriendMovements(String page, String pagesize) {
    }
}
