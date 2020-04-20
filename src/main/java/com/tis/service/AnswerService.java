package com.tis.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tis.bean.Answer;
import com.tis.mapper.AnswerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService extends BaseService<Answer,AnswerMapper>{
    public PageInfo<Answer> getAnswersByLessonId(Integer questionId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Answer> answers = ((AnswerMapper)mapper).getAnswersByQuestionId(questionId);
        return new PageInfo<>(answers);
    }
}
