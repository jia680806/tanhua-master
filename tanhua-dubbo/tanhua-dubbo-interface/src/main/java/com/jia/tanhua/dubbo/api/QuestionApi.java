package com.jia.tanhua.dubbo.api;


import com.jia.tanhua.domain.Question;

public interface QuestionApi {

    public Question findQuestionByUserId(Long userId);
}
