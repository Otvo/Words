package com.example.mapper;

import com.example.entity.BookwordsExample;
import com.example.entity.BookwordsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookwordsMapper {
    int countByExample(BookwordsExample example);

    int deleteByExample(BookwordsExample example);

    int deleteByPrimaryKey(BookwordsKey key);

    int insert(BookwordsKey record);

    int insertSelective(BookwordsKey record);

    List<BookwordsKey> selectByExample(BookwordsExample example);

    int updateByExampleSelective(@Param("record") BookwordsKey record, @Param("example") BookwordsExample example);

    int updateByExample(@Param("record") BookwordsKey record, @Param("example") BookwordsExample example);
}