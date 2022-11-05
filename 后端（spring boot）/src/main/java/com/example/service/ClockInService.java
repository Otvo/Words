package com.example.service;

import com.example.entity.BooksExample;
import com.example.entity.ClockInExample;
import com.example.entity.ClockInKey;
import com.example.mapper.ClockInMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClockInService {
    @Autowired
    ClockInMapper clockInMapper;

    public List<ClockInKey> getClockIn(String id){
        ClockInExample clockInExample = new ClockInExample();
        ClockInExample.Criteria cri=clockInExample.createCriteria();
        cri.andUseridEqualTo(id);
        List<ClockInKey> clockInKey=clockInMapper.selectByExample(clockInExample);
//        List<Date> date = new ArrayList<Date>();
//        for(int i=0;i<clockInKey.size();i++){
//            System.out.println(clockInKey.get(i).getDate());
//            date.add(clockInKey.get(i).getDate());
//        }
        return clockInKey;
    }
}
