package com.example.service;

import com.example.DemoApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @DisplayName("登录验证")
    public void verify() {
        boolean ok = userService.verify("001", "123");
        assertTrue(ok);
    }


//    @Test
//    void getUserInformation() {
//
//    }
}
//由于DAO层的方法直接操作数据库，为避免测试数据对数据库造成污染，使用注解@Transactional和@Rollback在测试完成后对测试数据进行回滚。
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ScoreControllerTestNew {
//
//    @Autowired
//    private UserDao userDao;
//
//    /**
//     * 测试插入数据
//     */
//    @Test
//    @Rollback(value = true)
//    @Transactional
//    public void testInsert() {
//        User userZhang = new User();
//        userZhang.setName("zhangSan");
//        userZhang.setAge(23);
//        userZhang.setGender(0);
//        userZhang.setEmail("123@test.com");
//        int n = userDao.insert(userZhang);
//        Assert.assertEquals(1, n);
//    }
//}