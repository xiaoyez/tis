package com.tis.mapper;


import com.tis.bean.Info;
import com.tis.common.BaseDto;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InfoMapper extends Mapper<Info> {
    @Select("select info.*,`user`.user_name as publisher from info inner join user on info.publisher_id = user.id")
    List<Info> getInfoList();
}

