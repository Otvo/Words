package com.example.mapper;

import com.example.entity.Userbooks;
import com.example.entity.UserbooksExample;
import com.example.entity.UserbooksKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserbooksMapper {
    int countByExample(UserbooksExample example);

    int deleteByExample(UserbooksExample example);

    int deleteByPrimaryKey(UserbooksKey key);

    int insert(Userbooks record);

    int insertSelective(Userbooks record);

    List<Userbooks> selectByExample(UserbooksExample example);

    Userbooks selectByPrimaryKey(UserbooksKey key);

    int updateByExampleSelective(@Param("record") Userbooks record, @Param("example") UserbooksExample example);

    int updateByExample(@Param("record") Userbooks record, @Param("example") UserbooksExample example);

    int updateByPrimaryKeySelective(Userbooks record);

    int updateByPrimaryKey(Userbooks record);
}