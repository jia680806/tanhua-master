package com.jia.tanhua.dubbo.api;


import com.jia.tanhua.domain.Question;

public interface QuestionApi {
    public Question findQuestionById(Long userId);
}
