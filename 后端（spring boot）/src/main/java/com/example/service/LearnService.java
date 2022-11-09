package com.example.service;

import com.example.entity.*;
import com.example.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LearnService {
    @Autowired
    LearnMapper learnMapper;
    @Autowired
    UserbooksMapper userbooksMapper;
    @Autowired
    BooksMapper booksMapper;
    @Autowired
    BookwordsMapper bookwordsMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    DicMapper dicMapper;

    public List<Dic> getLearnWords(String id){
        int bookid;
        int length;
        int sum;
        int learnNum;
        int reviewNum;
        Date date=new Date();
//        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
//        String time = "2021-5-21";
//        Date parse = simpleDateFormat.parse(time);
//        System.out.println(parse);


        //在userbook里面找用户当前正在学习书籍的id
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();

        //在learn里面找正在学习这本书籍它已经学了的单词
        LearnExample learnExample = new LearnExample();
        LearnExample.Criteria cri_1=learnExample.createCriteria();
        cri_1.andUseridEqualTo(id).andBookidEqualTo(bookid);
        List<Learn> learn=learnMapper.selectByExample(learnExample);//以及学习了的单词字段
        if(learn==null){//如果这本书从来没有学过单词
            length=0;//那么把学了的单词长度设置为0
        }
        else {
            length = learn.size();//否则长度为学了单词字段的长度
        }
        //通过正在学习书籍的bookid在bookwords里面查找这本书一共有多少个单词字段
        BookwordsExample bookwordsExample = new BookwordsExample();
        BookwordsExample.Criteria cri_2=bookwordsExample.createCriteria();
        cri_2.andBookidEqualTo(bookid);
        List<BookwordsKey> bookwordsKey=bookwordsMapper.selectByExample(bookwordsExample);

        //在用户表通过用户id查询用户每日设定的学习单词数
        learnNum=usersMapper.selectByPrimaryKey(id).getLearnnum();


        //当前正在学习书籍的单词总数就是这本书里单词字段的大小
        sum=bookwordsKey.size();
        String[] totalwords=new String[sum];//这本书籍所有单词的字符串数组
        String[] learnwords=new String[length];//已经学习单词的字符串数组
        LinkedList<String> list = new LinkedList<String>();//list作为他们的差集

//        sum=booksMapper.selectByPrimaryKey(bookid).getSum();

        List<Dic> dic=new ArrayList<>();//声明一个空返回类型

        for(int i=0;i<sum;i++){
            totalwords[i]=bookwordsKey.get(i).getWord();
        }
        for(int j=0;j<length;j++){
            learnwords[j]=learn.get(j).getWord();
        }
        for(String str:totalwords){
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for(String str:learnwords){
            if (list.contains(str)) {
                list.remove(str);
            }
        }

        List<String> words_string=new ArrayList<String>();//声明一个今天需要学习多少单词的数组，如果今天已经学了一部分，那么数组大小会对照减少

        //查找今天时间内学习了多少单词
        cri_1.andDateEqualTo(date);
        int todayalready;//今天已经学了多少单词数目
        todayalready=learnMapper.selectByExample(learnExample).size();


        for(int k=0;k<learnNum-todayalready;k++){//要学习单词数目减去今天已经学习单词数目
            words_string.add(list.get(k));//从字典找没有学过并且今天学要学习的单词
        }
        
        if(words_string.size()==0){//如果今天已经学完了单词
            return dic;
        }
        else
        {
            //在字典里找符合条件的单词字典 字段
            DicExample dicExample = new DicExample();
            DicExample.Criteria cri_3=dicExample.createCriteria();
            cri_3.andWordIn(words_string);
            dic=dicMapper.selectByExample(dicExample);

            return dic;
        }


    }

    public List<Learn> getReviewWords(String id){
        //在userbook里面找用户当前正在学习书籍的id
        UserService userService=new UserService();
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();

        //在learn里面找正在学习这本书籍它已经学了的单词
        LearnExample learnExample = new LearnExample();
        LearnExample.Criteria cri_1=learnExample.createCriteria();
        cri_1.andUseridEqualTo(id).andBookidEqualTo(bookid).andMasterLessThan(5);
        List<Learn> learn=learnMapper.selectByExample(learnExample);//以及学习了的单词字段
        return learn;
//        List<Dic> dic=new ArrayList<>();//声明一个空返回类型
//        List<String> words_string=new ArrayList<String>();
//        if(learn==null){
//            return dic;
//        }
//        else{
//            if(reviewNum>learn.size()){
//                for(int i=0;i<learn.size();i++){
//                    words_string.add(learn.get(i).getWord());
//                }
//            }
//            else{
//                int []rmd=randomArray(0,learn.size()-1,reviewNum);
//                System.out.print(rmd);
//                for(int i=0;i<reviewNum;i++){
//                    words_string.add(learn.get(rmd[i]).getWord());
//                }
//            }
//            //在字典里找符合条件的单词字典 字段
//            DicExample dicExample = new DicExample();
//            DicExample.Criteria cri_3=dicExample.createCriteria();
//            cri_3.andWordIn(words_string);
//            dic=dicMapper.selectByExample(dicExample);


    }
    //随机取不重复的数
    public static int[] randomArray(int min,int max,int n){
        int len = max-min+1;

        if(max < min || n > len){
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min+len; i++){
            source[i-min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

    public void learnWords(String id,String word,int master){
        //在userbook里面找用户当前正在学习书籍的id
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        Date date=new Date();
        Learn learn=new Learn();
        learn.setMaster(master);
        learn.setUserid(id);
        learn.setBookid(bookid);
        learn.setWord(word);
        learn.setDate(date);
        learnMapper.insertSelective(learn);
    }

    public void reviewWords(String id,String word,int master){
        //在userbook里面找用户当前正在学习书籍的id
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        LearnKey learnKey=new LearnKey();
        learnKey.setUserid(id);
        learnKey.setBookid(bookid);
        learnKey.setWord(word);
        Learn learn=learnMapper.selectByPrimaryKey(learnKey);
        int changemaster=learn.getMaster()+master-1;
        if(changemaster<0){
            learn.setMaster(0);
        }
        else{
            learn.setMaster(learn.getMaster()+master-1);
        }
        learnMapper.updateByPrimaryKey(learn);
    }

    public void clearRecord(String id,int bookid){
        LearnExample learnExample=new LearnExample();
        LearnExample.Criteria cri=learnExample.createCriteria();
        cri.andUseridEqualTo(id).andBookidEqualTo(bookid);
        learnMapper.deleteByExample(learnExample);
    }

    public boolean isCollect(String id,String word){
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        LearnKey learnKey=new LearnKey();
        learnKey.setUserid(id);
        learnKey.setBookid(bookid);
        learnKey.setWord(word);
        return learnMapper.selectByPrimaryKey(learnKey).getCollect();
    }

    public void collect(String id,String word){
        UserbooksExample userbooksExample = new UserbooksExample();
        UserbooksExample.Criteria cri=userbooksExample.createCriteria();
        cri.andUseridEqualTo(id).andIsstudyingEqualTo(true);
        int bookid=userbooksMapper.selectByExample(userbooksExample).get(0).getBookid();
        LearnKey learnKey=new LearnKey();
        learnKey.setUserid(id);
        learnKey.setBookid(bookid);
        learnKey.setWord(word);
        Learn learn=learnMapper.selectByPrimaryKey(learnKey);
        learn.setCollect(!learn.getCollect());
        learnMapper.updateByPrimaryKey(learn);
    }
}
