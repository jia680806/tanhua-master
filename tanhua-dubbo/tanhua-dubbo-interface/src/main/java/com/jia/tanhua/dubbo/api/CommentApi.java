package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.enums.CommentType;
import com.jia.tanhua.mongo.Comment;
import com.jia.tanhua.vo.PageResult;

import java.util.List;

public interface CommentApi {
    Integer save(Comment comment);


    List<Comment> findComments(String movementId, CommentType comment, Integer page, Integer pagesize);
}
