package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.Dic;
import com.example.entity.Users;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class    UserController {
    @Autowired
    private UserService usersService;
    //登录接口
    @RequestMapping("login")
    public String GetUser(@RequestParam(value = "userID",required = true) String id)
    {
        return usersService.selectByPrimaryKey(id).toString();
    }

    //登录验证接口
    @RequestMapping("verify")
    public JSONObject verify(@RequestParam(value = "userID",required = true) String id,
                             @RequestParam(value = "password",required = true) String password) {

        JSONObject json=new JSONObject();
        JSONObject json_1=new JSONObject();
        boolean bool=usersService.verify(id,password);

        if (bool==true) {
            Users users=usersService.getUserInformation(id);
            json_1.put("boolean",bool);
            json.put("name",users.getName());
            json.put("userID",id);
            json.put("learnNum",users.getLearnnum());
            json.put("reviewNum",users.getReviewnum());
            json.put("isClock",usersService.judgeclockin(id));
            json.put("pronunciation",users.getPronunciation());
            json.put("node",users.getNode());
            json.put("time",users.getTime());
            json_1.put("information",json);
        }
        else{
            json_1.put("boolean",bool);
        }
        return json_1;
    }

    //注册接口
    @RequestMapping("register")
    public JSONObject register(@RequestParam(value = "userID",required = true) String id,
                               @RequestParam(value = "password",required = true) String password,
                               @RequestParam(value = "name",required = true) String name) {

        JSONObject json=new JSONObject();
        json.put("success",usersService.register(id,password,name));
        return  json;
    }

    //设置每日单词学习量
    @RequestMapping("setLearnNum")
    public void setLearnNum(@RequestParam(value = "userID",required = true) String id,
                            @RequestParam(value = "learnNum",required = true) int learnNum){
        usersService.setLearnNum(id,learnNum);
    }

    //设置每日单词复习量
    @RequestMapping("setReviewNum")
    public void setReviewNum(@RequestParam(value = "userID",required = true) String id,
                            @RequestParam(value = "reviewNum",required = true) int reviewNum){
        usersService.setReviewNum(id,reviewNum);
    }


    //获取用户信息接口
    @RequestMapping("getUserInformation")
    public Users getUserInformation(@RequestParam(value = "userID",required = true) String id){
        return usersService.getUserInformation(id);
    }

    //打卡接口
    @RequestMapping("userSign")
    public int userSign(@RequestParam(value = "userID",required = true) String id){
        return usersService.userSign(id);
    }

    //获取收藏的单词接口
    @RequestMapping("getCollectWords")
    public JSONArray getMissedWords(@RequestParam(value = "userID",required = true) String id){
        List<Dic> dics=usersService.getCollectWords(id);
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
            jsonArray.add(object);
        }
        return jsonArray;
    }

    //今日是否打卡
    @RequestMapping("judgeClockIn")
    public JSONObject judgeclockin(@RequestParam(value = "userID",required = true) String id){

        JSONObject json=new JSONObject();
        json.put("isClock",usersService.judgeclockin(id));
        return json;
    }

    //今天学习了多少单词
    @RequestMapping("todayLearnWords")
    public JSONObject todayLearnWords(@RequestParam(value = "userID",required = true) String id){
        JSONObject json=new JSONObject();
        json.put("learn",usersService.todayLearnWords(id));
        return json;
    }

    //今天复习了多少单词
    @RequestMapping("totalReviewWords")
    public JSONObject totalReviewWords(@RequestParam(value = "userID",required = true) String id){
        JSONObject json=new JSONObject();
        json.put("totalreview",usersService.totalReviewWords(id));
        return json;
    }

    //设置发音
    @RequestMapping("setPronunciation")
    public void setPronunciation(@RequestParam(value = "userID",required = true) String id,
                                 @RequestParam(value = "pronunciation",required = true) String pronunciation){
        usersService.setPronunciation(id,pronunciation);
    }

    //设置是否提醒
    @RequestMapping("setNode")
    public void setNode(@RequestParam(value = "userID",required = true) String id,
                                 @RequestParam(value = "node",required = true) boolean node){
        usersService.setNode(id,node);
    }

    //设置提醒时间
    @RequestMapping("setNodeTime")
    public void setNodeTime(@RequestParam(value = "userID",required = true) String id,
                        @RequestParam(value = "time",required = true) Date time){
        usersService.setNodeTime(id,time);
    }

    //修改用户昵称
    @RequestMapping("updateName")
    public void updateName(@RequestParam(value = "userID",required = true) String id,
                           @RequestParam(value = "name",required = true) String name){
        usersService.updateName(id,name);
    }

    //注销用户
    @RequestMapping("logout")
    public void logout(@RequestParam(value = "userID",required = true) String id){
        usersService.logout(id);
    }
    @RequestMapping("updatepwd")
    public void updatepwd(@RequestParam(value = "userID",required = true) String id,
                          @RequestParam(value = "password",required = true) String password){
        usersService.updatepwd(id,password);
    }
}
