package com.example.service;

import com.example.entity.Books;
import com.example.mapper.BooksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.entity.BooksExample;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BooksMapper booksMapper;
    public List<Books> AllBooks(){
        BooksExample booksExample = new BooksExample();
        BooksExample.Criteria cri=booksExample.createCriteria();
        cri.andBookidIsNotNull();
        return booksMapper.selectByExample(booksExample);
    }

    public Books getBook(int id){
        return booksMapper.selectByPrimaryKey(id);
    }



}
