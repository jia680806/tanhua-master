package com.jia.tanhua.server.service;

import cn.hutool.core.collection.CollUtil;
import com.jia.tanhua.domain.UserInfo;
import com.jia.tanhua.dubbo.api.CommentApi;
import com.jia.tanhua.dubbo.api.UserInfoApi;
import com.jia.tanhua.enums.CommentType;
import com.jia.tanhua.mongo.Comment;
import com.jia.tanhua.server.interceptor.BaseContext;
import com.jia.tanhua.vo.CommentVo;
import com.jia.tanhua.vo.PageResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.bson.types.ObjectId;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    @DubboReference
    private CommentApi commentApi;

    @DubboReference
    private UserInfoApi userInfoApi;

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


    public PageResult findComments(Integer page, Integer pagesize,String movementId) {
        List<Comment> comments =  commentApi.findComments(movementId,CommentType.COMMENT,page,pagesize);
        if (CollUtil.isEmpty(comments)){
            new PageResult();
        }
        List<Long> userIds = CollUtil.getFieldValues(comments, "userId", Long.class);
        Map<Long, UserInfo> userInfos = userInfoApi.findByIds(userIds, null);


        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {

            UserInfo userInfo = userInfos.get(comment.getUserId());

            if (userInfo!=null){
                CommentVo vo = CommentVo.init(userInfo, comment);
                commentVos.add(vo);
            }

        }
        return new PageResult(page,pagesize,0,commentVos);

    }
}
