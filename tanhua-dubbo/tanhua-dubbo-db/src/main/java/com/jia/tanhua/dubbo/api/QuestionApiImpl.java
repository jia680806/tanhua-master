package com.jia.tanhua.dubbo.api;

import com.jia.tanhua.domain.Question;
import com.jia.tanhua.dubbo.mappers.QuestionMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class QuestionApiImpl implements QuestionApi{
    @Autowired
    private QuestionMapper questionMapper;

    public Question getQuestionMapper(Long id) {
        Question question = questionMapper.selectById(id);
        return question;
    }
}
