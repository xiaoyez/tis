package com.tis.mapper;


import com.tis.bean.Floor;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FloorMapper extends Mapper<Floor> {

    @Select("select floor.*,user.user_name as sender_name from floor inner join user on floor.sender_id = user.id where floor.topic_id = #{topicId}")
    List<Floor> getFloorListByTopicId(Integer topicId);
}

