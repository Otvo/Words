package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    ClockInMapper clockinMapper;
    @Autowired
    LearnMapper learnMapper;
    @Autowired
    DicMapper dicMapper;
    @Autowired
    UserbooksMapper userbooksMapper;

    public Users selectByPrimaryKey(String id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    public boolean verify(String id, String password) {
        if (password.equals(usersMapper.selectByPrimaryKey(id).getPassword()))
            return true;
        else
            return false;
    }

    public int register(String id, String password, String name) {
        Calendar c = Calendar.getInstance();//默认是当前日期
        System.out.print(c);
        // 获得年份
        int year = c.get(Calendar.YEAR);
// 获得月份
        int month = c.get(Calendar.MONTH);
// 获得日期
        int day = c.get(Calendar.DATE);
        c.set(year,month,day,20,0,0);
        Date date=c.getTime();
        Users user = new Users();
        user.setUserid(id);
        user.setPassword(password);
        user.setName(name);
        user.setPronunciation("en");
        user.setNode(true);
        user.setTime(date);
        if (usersMapper.selectByPrimaryKey(id) == null)
            return usersMapper.insert(user);
        else
            return 0;
    }

    //将date转化为datetime
    public static java.sql.Timestamp dtot(java.util.Date d) {
        if (null == d)
            return null;
        return new java.sql.Timestamp(d.getTime());
    }

    public void setLearnNum(String id, int learnNum) {
        Users users_1 = usersMapper.selectByPrimaryKey(id);
        Users users = new Users();
        users.setName(users_1.getName());
        users.setPassword(users_1.getPassword());
        users.setLearnnum(learnNum);
        users.setReviewnum(users_1.getReviewnum());
        users.setUserid(users_1.getUserid());
        usersMapper.updateByPrimaryKey(users);

    }

    public void setReviewNum(String id, int reviewNum) {
        Users users_1 = usersMapper.selectByPrimaryKey(id);
        Users users = new Users();
        users.setName(users_1.getName());
        users.setPassword(users_1.getPassword());
        users.setLearnnum(users_1.getLearnnum());
        users.setReviewnum(reviewNum);
        users.setUserid(users_1.getUserid());
        usersMapper.updateByPrimaryKey(users);

    }

    //
    public Users getUserInformation(String id) {
        return usersMapper.selectByPrimaryKey(id);
    }

    public int userSign(String id) {
        int duration;
        Date date = new Date();
        ClockInKey clockinkey = new ClockInKey();
        clockinkey.setUserid(id);
        clockinkey.setDate(date);

        ClockInExample clockinExample = new ClockInExample();
        ClockInExample.Criteria cri = clockinExample.createCriteria();
        cri.andUseridEqualTo(id);
        clockinMapper.insertSelective(clockinkey);

        duration = clockinMapper.selectByExample(clockinExample).size();
        return duration;
    }

    public List<Dic> getCollectWords(String id) {
        int length;

        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);

        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();

        LearnExample learnExample = new LearnExample();
        LearnExample.Criteria cri_1 = learnExample.createCriteria();
        cri_1.andUseridEqualTo(id).andBookidEqualTo(bookid).andCollectEqualTo(true);
        List<Learn> learn = learnMapper.selectByExample(learnExample);
        length=learn.size();
        List<Dic> dic=new ArrayList<>();//声明一个空返回类型
        List<String> words_string=new ArrayList<String>();
        for (int j = 0; j < length; j++) {
            words_string.add(learn.get(j).getWord());

        }
        if(words_string.size()==0){//如果今天已经学完了单词
            return dic;
        }
        else{
            DicExample dicExample = new DicExample();
            DicExample.Criteria cri_3=dicExample.createCriteria();
            cri_3.andWordIn(words_string);
            dic=dicMapper.selectByExample(dicExample);
            return dic;
        }
    }

    public boolean judgeclockin(String id){
        Date date=new Date();
        ClockInExample clockInExample=new ClockInExample();
        ClockInExample.Criteria cri=clockInExample.createCriteria();
        cri.andUseridEqualTo(id).andDateEqualTo(date);
        if(clockinMapper.selectByExample(clockInExample).size()==0){
            return false;
        }
        else {
            return true;
        }
    }

    public int todayLearnWords(String id){
        Date date=new Date();
        //在userbook里面找用户当前正在学习书籍的id
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        //在learn里面找正在学习这本书籍它今天已经学了的单词
        LearnExample learnExample = new LearnExample();
        LearnExample.Criteria cri_1=learnExample.createCriteria();
        cri_1.andUseridEqualTo(id).andBookidEqualTo(bookid).andDateEqualTo(date);
        List<Learn> learn=learnMapper.selectByExample(learnExample);
        return learn.size();
    }

    public int totalReviewWords(String id){
        //在userbook里面找用户当前正在学习书籍的id
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();

        //在learn里面找正在学习这本书籍它已经复习了的单词
        LearnExample learnExample = new LearnExample();
        LearnExample.Criteria cri_1=learnExample.createCriteria();
        cri_1.andUseridEqualTo(id).andBookidEqualTo(bookid).andMasterBetween(0,5);
        List<Learn> review=learnMapper.selectByExample(learnExample);
        return review.size();
    }

    public int totalReviewWords2(String id,int bookid){
        //在learn里面找正在学习这本书籍它已经复习了的单词
        LearnExample learnExample = new LearnExample();
        LearnExample.Criteria cri_1=learnExample.createCriteria();
        cri_1.andUseridEqualTo(id).andBookidEqualTo(bookid).andMasterBetween(0,5);
        List<Learn> review=learnMapper.selectByExample(learnExample);
        return review.size();
    }
    public void setPronunciation(String id,String pronunciation){
        Users users=usersMapper.selectByPrimaryKey(id);
        users.setPronunciation(pronunciation);
        usersMapper.updateByPrimaryKey(users);
    }

    public void setNode(String id,boolean node){
        Users users=usersMapper.selectByPrimaryKey(id);
        users.setNode(node);
        usersMapper.updateByPrimaryKey(users);
    }

    public void setNodeTime(String id,Date time){
        Users users=usersMapper.selectByPrimaryKey(id);
        users.setTime(time);
        usersMapper.updateByPrimaryKey(users);
    }

    public void updateName(String id,String name){
        Users users=usersMapper.selectByPrimaryKey(id);
        users.setName(name);
        usersMapper.updateByPrimaryKey(users);
    }

    public void logout(String id){
        ClockInExample clockinExample = new ClockInExample();
        ClockInExample.Criteria cri = clockinExample.createCriteria();
        cri.andUseridEqualTo(id);
        clockinMapper.deleteByExample(clockinExample);

        //先删除学习记录
        LearnExample learnExample=new LearnExample();
        LearnExample.Criteria cri_1=learnExample.createCriteria();
        cri_1.andUseridEqualTo(id);
        learnMapper.deleteByExample(learnExample);

        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri_2=userbooksExample.createCriteria();
        cri_2.andUseridEqualTo(id);
        userbooksMapper.deleteByExample(userbooksExample);

        usersMapper.deleteByPrimaryKey(id);
    }

    public void updatepwd(String id,String pwd){
        Users users=usersMapper.selectByPrimaryKey(id);
        users.setPassword(pwd);
        usersMapper.updateByPrimaryKey(users);
    }
}

