package com.example.service;

import com.example.entity.*;
import com.example.mapper.BooksMapper;
import com.example.mapper.LearnMapper;
import com.example.mapper.UserbooksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserBooksService {
    @Autowired
    UserbooksMapper userbooksMapper;
    @Autowired
    BooksMapper booksMapper;
    @Autowired
    LearnMapper learnMapper;


    public boolean judgeisStudying(String id,int bookid){
        UserbooksKey userbooksKey=new UserbooksKey();
        userbooksKey.setUserid(id);
        userbooksKey.setBookid(bookid);
        return userbooksMapper.selectByPrimaryKey(userbooksKey).getIsstudying();
    }

    public int study(String id,int bookid){
        LearnExample learnExample=new LearnExample();
        LearnExample.Criteria cri=learnExample.createCriteria();
        cri.andUseridEqualTo(id).andBookidEqualTo(bookid);
        return learnMapper.selectByExample(learnExample).size();
    }

    public Books getLearnningBook(String id){
        Boolean isStudying=true;
        int bookid;
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(isStudying);

        bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        return booksMapper.selectByPrimaryKey(bookid);
    }

    public List<Books> getUserBook(String id){
        int length;
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id);
        length=userbooksMapper.selectByExample(userbooksExample).size();
        List<Userbooks> userbooks=userbooksMapper.selectByExample(userbooksExample);
        List<Integer> bookids=new ArrayList<Integer>();
        List<Books> books;
        for(int i=0;i<length;i++){
            bookids.add(userbooks.get(i).getBookid());

        }
        BooksExample booksExample = new BooksExample();
        BooksExample.Criteria cri_1=booksExample.createCriteria();
        cri_1.andBookidIn(bookids);
        books=booksMapper.selectByExample(booksExample);
        return books;
    }

    public void setLearnningBook(String id,int bookid){
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        Userbooks userbooks_1=new Userbooks();
        int bookid_1=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        userbooks_1.setUserid(id);
        userbooks_1.setBookid(bookid_1);
        userbooks_1.setIsstudying(false);

        userbooksMapper.updateByExample(userbooks_1,userbooksExample);
        Userbooks userbooks=new Userbooks();
        userbooks.setUserid(id);
        userbooks.setBookid(bookid);
        userbooks.setIsstudying(true);
        userbooksMapper.updateByPrimaryKey(userbooks);
    }

    public void addBook(String id,int bookid){
        Userbooks userbooks=new Userbooks();
        userbooks.setUserid(id);
        userbooks.setBookid(bookid);
        userbooks.setIsstudying(false);
        userbooksMapper.insertSelective(userbooks);
    }

    public void deleteBook(String id,int bookid){
        //先删除学习记录
        LearnExample learnExample=new LearnExample();
        LearnExample.Criteria cri=learnExample.createCriteria();
        cri.andUseridEqualTo(id).andBookidEqualTo(bookid);
        learnMapper.deleteByExample(learnExample);
        //再删除图书
        UserbooksKey userbooksKey=new UserbooksKey();
        userbooksKey.setUserid(id);
        userbooksKey.setBookid(bookid);
        Userbooks userbooks=new Userbooks();
        userbooks=userbooksMapper.selectByPrimaryKey(userbooksKey);
        userbooksMapper.deleteByPrimaryKey(userbooksKey);
        if(userbooks.getIsstudying()==true){
            Userbooks userbooks_1=new Userbooks();
            userbooks_1.setUserid(id);
            userbooks_1.setBookid(-1);
            userbooks_1.setIsstudying(true);
            userbooksMapper.updateByPrimaryKey(userbooks_1);
        }

    }

}
