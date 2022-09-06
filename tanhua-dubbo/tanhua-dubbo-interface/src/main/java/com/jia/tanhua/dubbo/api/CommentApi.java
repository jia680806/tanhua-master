package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.mongo.Comment;

public interface CommentApi {
    Integer save(Comment comment);
}
