package com.tis.mapper;


import com.tis.bean.Answer;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AnswerMapper extends Mapper<Answer> {

    @Select("select answer.*,`user`.user_name as student_name from answer inner join user on answer.student_id  = user.id where answer.question_id = #{questionId} ")
    List<Answer> getAnswersByQuestionId(Integer questionId);
}

