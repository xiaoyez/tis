package com.tis.mapper;


import com.tis.bean.Homework;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HomeworkMapper extends Mapper<Homework> {
    @Select("SELECT homework.*,`user`.user_name as student_name,lesson.`name` as lesson_name FROM homework INNER JOIN lesson ON homework.lesson_id = lesson.id INNER JOIN `user` on homework.student_id = user.id WHERE lesson.launcher_id = #{teacherId}")
    List<Homework> getHomeworkList(Integer teacherId);
}

