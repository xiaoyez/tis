package com.tis.service;

import com.tis.bean.Question;
import com.tis.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

@Service
public class QuestionService extends BaseService<Question,QuestionMapper> {
    public Question getOnQuestionByLessonId(Integer lessonId) {
        Question question = new Question();
        question.setLessonId(lessonId);
        question.setState(1);
        return get(question);
    }
}
