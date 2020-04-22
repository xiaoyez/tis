package com.tis.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tis.bean.Info;
import com.tis.mapper.InfoMapper;
import org.springframework.stereotype.Service;

@Service
public class InfoService extends BaseService<Info,InfoMapper> {
    public PageInfo<Info> getInfoList(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(((InfoMapper)mapper).getInfoList());
    }

}
