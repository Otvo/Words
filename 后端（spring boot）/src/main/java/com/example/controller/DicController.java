package com.example.controller;

import com.example.entity.Books;
import com.example.entity.Dic;
import com.example.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/dic")
public class DicController {
    @Autowired
    private DicService dicService;
    @RequestMapping("getWordInfo")
    public Dic getWordInfo(@RequestParam(value = "word",required = true) String word){
        return dicService.getWordInfo(word);
    }

}
