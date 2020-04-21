package com.tis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tis.bean.Lesson;
import com.tis.bean.User;
import com.tis.mapper.LessonMapper;
import org.springframework.stereotype.Service;

@Service
public class LessonService extends BaseService<Lesson,LessonMapper> {
    public Lesson getOnLessonByStudentId(Integer studentId) {
        return ((LessonMapper)mapper).getOnLessonByStudentId(studentId);
    }


    public PageInfo<Lesson> getHasJoinLessonByStudentId(Integer studentId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(((LessonMapper)mapper).getHasJoinLessonByStudentId(studentId));
    }
}
