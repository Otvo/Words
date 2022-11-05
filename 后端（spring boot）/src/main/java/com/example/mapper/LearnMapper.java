package com.example.mapper;

import com.example.entity.Learn;
import com.example.entity.LearnExample;
import com.example.entity.LearnKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LearnMapper {
    int countByExample(LearnExample example);

    int deleteByExample(LearnExample example);

    int deleteByPrimaryKey(LearnKey key);

    int insert(Learn record);

    int insertSelective(Learn record);

    List<Learn> selectByExample(LearnExample example);

    Learn selectByPrimaryKey(LearnKey key);

    int updateByExampleSelective(@Param("record") Learn record, @Param("example") LearnExample example);

    int updateByExample(@Param("record") Learn record, @Param("example") LearnExample example);

    int updateByPrimaryKeySelective(Learn record);

    int updateByPrimaryKey(Learn record);
}