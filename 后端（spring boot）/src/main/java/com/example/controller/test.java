package com.example.controller;

import com.example.DemoApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class test {
    @RequestMapping("hello")
    public String test() {
        return "hello Word";
    }
}
