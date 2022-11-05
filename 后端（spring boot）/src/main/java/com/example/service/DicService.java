package com.example.service;

import com.example.entity.Dic;
import com.example.mapper.DicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicService {
    @Autowired
    DicMapper dicMapper;

    public Dic getWordInfo(String word){
        return dicMapper.selectByPrimaryKey(word);
    }
}
