package com.tis.mapper;


import com.tis.bean.Topic;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TopicMapper extends Mapper<Topic> {
    @Select("SELECT topic.*,`user`.user_name as sender_name FROM topic INNER JOIN `user` on topic.sender_id = `user`.id")
    List<Topic> getTopicList();
}

