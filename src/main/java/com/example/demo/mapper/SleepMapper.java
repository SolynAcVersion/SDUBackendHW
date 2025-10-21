package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.data.po.Sleep;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SleepMapper extends BaseMapper<Sleep> {

    @Insert("INSERT INTO sleep (start_time, end_time) VALUES (#{startTime}, #{endTime})")
    int insertStartTime(Sleep sleep);
}
