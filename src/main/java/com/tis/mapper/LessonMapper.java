package com.tis.mapper;


import com.tis.bean.Lesson;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LessonMapper extends Mapper<Lesson> {
    @Select("select lesson.* from lesson inner join sign_in on lesson.id = sign_in.lesson_id where lesson.state != 0 && sign_in.student_id = #{studentId}")
    Lesson getOnLessonByStudentId(Integer studentId);

    @Select("select lesson.* from lesson inner join sign_in on lesson.id = sign_in.lesson_id where sign_in.student_id = #{studentId}")
    List<Lesson> getHasJoinLessonByStudentId(Integer studentId);

    @Select("select lesson.* from lesson where lesson.state != 0 && launcher_id = #{launcherId}")
    Lesson getOnLessonByTeacherId(Integer launcherId);
}

