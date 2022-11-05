package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.ClockInKey;
import com.example.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/clockin")
public class ClockInController {
    @Autowired
    private ClockInService clockInService;

    @RequestMapping("getClockIn")
    public String[] getClockIn(@RequestParam(value = "userID",required = true) String id){
        List<ClockInKey> clockInKeys=clockInService.getClockIn(id);
        String array[] =new String[clockInKeys.size()];
        for(int i=0;i<clockInKeys.size();i++){
            ClockInKey clockInKey=clockInKeys.get(i);
            Date date=clockInKey.getDate();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String transformDate=simpleDateFormat.format(date);
            array[i]=transformDate;

        }
        return array;

    }

}
