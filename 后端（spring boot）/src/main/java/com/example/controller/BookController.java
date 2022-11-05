package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Books;
import com.example.service.BookService;
import com.example.service.UserBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/book")
public class BookController {
        @Autowired
        private BookService bookService;
        @Autowired
        private UserBooksService userBooksService;

        @RequestMapping("getAllBook")
        public List<Books> GetUser(){
            return bookService.AllBooks();
        }



}
