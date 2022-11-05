package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.Books;
import com.example.entity.Dic;
import com.example.entity.DicExample;
import com.example.entity.Learn;
import com.example.mapper.DicMapper;
import com.example.mapper.UsersMapper;
import com.example.service.LearnService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/learn")
public class LearnController {
    @Autowired
    private LearnService learnService;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    DicMapper dicMapper;

    @RequestMapping("getLearnWords")
    public JSONArray getLearnWords(@RequestParam(value = "userID",required = true) String id)  {
        List<Dic> dics=learnService.getLearnWords(id);
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<dics.size();i++){
            Dic dic=dics.get(i);
            JSONObject object = new JSONObject();
            String s = dic.getMeaning();
            List<String[]> meanings = new ArrayList<String[]>();
            Pattern pattern = Pattern.compile("(n.|vt.|v.|vi.|prep.|pron.|conj.|aux.|(adj.|a.)|adv.|art.|num.|u.|c.|pl.|(interj.|int.))[^a-z]*");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String[] res;
                res = matcher.group().split("\\.");
                if (res.length > 1) {
                    res[0] = res[0] + ".";
                    res[1] = res[1].replace(" ", "");
                    meanings.add(res);
                }
            }
            object.put("spell",dic.getWord());
            JSONArray Jsonarray = new JSONArray();
            Jsonarray = JSONArray.parseArray(JSON.toJSONString(meanings));
            object.put("meanings",Jsonarray);
            object.put("usAccent",dic.getUsAccent());
            object.put("enAccent",dic.getEnAccent());
            object.put("pronunciation","http://dict.youdao.com/dictvoice?type=0&audio=" + dic.getWord());
            object.put("isCollect",false);
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @RequestMapping("getReviewWords")
    public JSONArray getReviewWords(@RequestParam(value = "userID",required = true) String id){
        UserService userService=new UserService();
        List<Learn> learn=learnService.getReviewWords(id);
        List<Dic> dics=new ArrayList<>();//声明一个空返回类型
        JSONArray jsonArray=new JSONArray();
        List<String> words_string=new ArrayList<String>();
        //在用户表通过用户id查询用户每日设定的复习单词数
        int reviewNum=learn.size();
        if(learn==null){
            return jsonArray;
        }
        else {
            if (reviewNum > learn.size()) {
                for (int i = 0; i < learn.size(); i++) {
                    words_string.add(learn.get(i).getWord());
                }
            } else {
                int[] rmd = learnService.randomArray(0, learn.size() - 1, reviewNum);
                System.out.print(rmd);
                for (int i = 0; i < reviewNum; i++) {
                    words_string.add(learn.get(rmd[i]).getWord());
                }
            }
            //在字典里找符合条件的单词字典 字段
            DicExample dicExample = new DicExample();
            DicExample.Criteria cri_3 = dicExample.createCriteria();
            cri_3.andWordIn(words_string);
            dics = dicMapper.selectByExample(dicExample);
        }


        for(int i=0;i<dics.size();i++) {
            Dic dic = dics.get(i);
            JSONObject object = new JSONObject();
            String s = dic.getMeaning();
            List<String[]> meanings = new ArrayList<String[]>();
            Pattern pattern = Pattern.compile("(n.|vt.|v.|vi.|prep.|pron.|conj.|aux.|(adj.|a.)|adv.|art.|num.|u.|c.|pl.|(interj.|int.))[^a-z]*");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                String[] res;
                res = matcher.group().split("\\.");
                if (res.length > 1) {
                    res[0] = res[0] + ".";
                    res[1] = res[1].replace(" ", "");
                    meanings.add(res);
                }
            }
            object.put("spell", dic.getWord());
            JSONArray Jsonarray = new JSONArray();
            Jsonarray = JSONArray.parseArray(JSON.toJSONString(meanings));
            object.put("meanings", Jsonarray);
            object.put("usAccent", dic.getUsAccent());
            object.put("enAccent", dic.getEnAccent());
            object.put("pronunciation", "http://dict.youdao.com/dictvoice?type=0&audio=" + dic.getWord());
            object.put("isCollect", learn.get(i).getCollect());
            jsonArray.add(object);
        }
        return jsonArray;
    }

    @RequestMapping("learnWords")
    public void learnWords(@RequestParam(value = "userID",required = true) String id,
                           @RequestParam(value = "word",required = true) String word,
                           @RequestParam(value = "master",required = true) int master){
        learnService.learnWords(id,word,master);
    }

    @RequestMapping("reviewWords")
    public void reviewWords(@RequestParam(value = "userID",required = true) String id,
                           @RequestParam(value = "word",required = true) String word,
                            @RequestParam(value = "master",required = true) int master){
        learnService.reviewWords(id,word,master);
    }

    @RequestMapping("clearBookRecord")
    public void clearRecord(@RequestParam(value = "userID",required = true) String id,
                            @RequestParam(value = "bookID",required = true) int bookid){
        learnService.clearRecord(id,bookid);
    }

    @RequestMapping("collect")
    public void clearRecord(@RequestParam(value = "userID",required = true) String id,
                            @RequestParam(value = "word",required = true) String word){
        learnService.collect(id,word);
    }
}
