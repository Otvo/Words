package com.example.controller;

import com.alibaba.fastjson.JSONArray;
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
@RequestMapping("/userbook")
public class UserBooksController {
    @Autowired
    private UserBooksService userBooksService;

    @RequestMapping("getLearnningBook")
    public JSONObject getLearnningBook(@RequestParam(value = "userID",required = true) String id){
        Books books=userBooksService.getLearnningBook(id);
        JSONObject json=new JSONObject();
        json.put("id",books.getBookid());
        json.put("name",books.getName());
        json.put("type",books.getType());
        json.put("sumWords",books.getSum());
        json.put("study",userBooksService.study(id,books.getBookid()));
        json.put("isStudying",true);
        json.put("image",books.getImage());
        return json;
    }

    @RequestMapping("getUserBook")
    public JSONArray getUserBook(@RequestParam(value = "userID",required = true) String id){
        List<Books> books=userBooksService.getUserBook(id);
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<books.size();i++){
            JSONObject object = new JSONObject();
            object.put("id",books.get(i).getBookid());
            object.put("name",books.get(i).getName());
            object.put("type",books.get(i).getType());
            object.put("sumWords",books.get(i).getSum());
            object.put("study",userBooksService.study(id,books.get(i).getBookid()));
            object.put("isStudying",userBooksService.judgeisStudying(id,books.get(i).getBookid()));
            object.put("image",books.get(i).getImage());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @RequestMapping("setLearnningBook")
    public void setLearnningBook(@RequestParam(value = "userID",required = true) String id,
                                 @RequestParam(value = "bookID",required = true) int bookid){
        userBooksService.setLearnningBook(id,bookid);
    }

    @RequestMapping("addBook")
    public void addBook(@RequestParam(value = "userID",required = true) String id,
                        @RequestParam(value = "bookID",required = true) int bookid){
        userBooksService.addBook(id,bookid);
    }

    @RequestMapping("deleteBook")
    public void deleteBook(@RequestParam(value = "userID",required = true) String id,
                        @RequestParam(value = "bookID",required = true) int bookid){
        userBooksService.deleteBook(id,bookid);
    }
}
