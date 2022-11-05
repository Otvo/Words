package com.example.mapper;

import com.example.entity.ClockInExample;
import com.example.entity.ClockInKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockInMapper {
    int countByExample(ClockInExample example);

    int deleteByExample(ClockInExample example);

    int deleteByPrimaryKey(ClockInKey key);

    int insert(ClockInKey record);

    int insertSelective(ClockInKey record);

    List<ClockInKey> selectByExample(ClockInExample example);

    int updateByExampleSelective(@Param("record") ClockInKey record, @Param("example") ClockInExample example);

    int updateByExample(@Param("record") ClockInKey record, @Param("example") ClockInExample example);
}