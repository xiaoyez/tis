package com.tis.service;

import com.github.pagehelper.PageHelper;
import com.tis.bean.Floor;
import com.tis.mapper.FloorMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorService extends BaseService<Floor,FloorMapper> {

    public List<Floor> getFloorListByTopicId(Integer topicId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return mapper.getFloorListByTopicId(topicId);
    }
}
