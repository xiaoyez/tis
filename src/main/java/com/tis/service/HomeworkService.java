package com.tis.service;

import com.github.pagehelper.PageHelper;
import com.tis.bean.Homework;
import com.tis.mapper.HomeworkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService extends BaseService<Homework, HomeworkMapper> {
    public List<Homework> getHomeworkList(Integer teacherId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return ((HomeworkMapper) mapper).getHomeworkList(teacherId);
    }
}
