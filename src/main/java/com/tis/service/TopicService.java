package com.tis.service;

import com.github.pagehelper.PageHelper;
import com.tis.bean.Topic;
import com.tis.mapper.TopicMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TopicService extends BaseService<Topic, TopicMapper> {

    public List<Topic> getTopicList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.getTopicList();
    }

    public Topic getByTopicId(Integer topicId) {
        return mapper.getByTopicId(topicId);
    }
}
