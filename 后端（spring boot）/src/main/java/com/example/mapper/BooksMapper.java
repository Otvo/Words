package com.example.mapper;

import com.example.entity.Books;
import com.example.entity.BooksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BooksMapper {
    int countByExample(BooksExample example);

    int deleteByExample(BooksExample example);

    int deleteByPrimaryKey(Integer bookid);

    int insert(Books record);

    int insertSelective(Books record);

    List<Books> selectByExample(BooksExample example);

    Books selectByPrimaryKey(Integer bookid);

    int updateByExampleSelective(@Param("record") Books record, @Param("example") BooksExample example);

    int updateByExample(@Param("record") Books record, @Param("example") BooksExample example);

    int updateByPrimaryKeySelective(Books record);

    int updateByPrimaryKey(Books record);
}