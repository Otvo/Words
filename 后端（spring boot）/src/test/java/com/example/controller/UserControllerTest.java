package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("测试登录验证接口")
    void verify() {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("userID", "001");
        uriVariables.put("password", "abc");

        JSONObject jsonObject = this.restTemplate.getForObject("http://127.0.0.1:" +port+"/user/verify?userID={userID}&password={password}",
               JSONObject.class, uriVariables);

        JSONObject userInfo = jsonObject.getJSONObject("information");
        assertAll("组合断言判断",
                () -> {
                    //id,密码核验
                   assertTrue(jsonObject.getBoolean("boolean"));
                },
                () -> {
                    //验证name
                   assertEquals(userInfo.getString("name"), "lyy");
                },
                () -> {
                    //学习单词数目
                    assertEquals(userInfo.getIntValue("learnNum"), 30);
                },
                () -> {
                    //复习单词数目
                   assertEquals(userInfo.getIntValue("reviewNum"), 30);
                });

    }
}
//restTemplate或者MockMvc
//参考1
//@Autowired
//private RestTemplate restTemplate;
//
//    /**
//     * 单元测试（带参的get请求），要将RestTemplate配置初始化为一个Bean
//     */
//    @Test
//    public void testGetByParam(){
//        //请求地址
//        String url = "http://localhost:8080/testGetByParam?userName={userName}&userPwd={userPwd}";
//
//        //请求参数
//        Map<String, String> uriVariables = new HashMap<>();
//        uriVariables.put("userName", "唐三藏");
//        uriVariables.put("userPwd", "123456");
//
//        //发起请求,直接返回对象（带参数请求）
//        ResponseBean responseBean = restTemplate.getForObject(url, ResponseBean.class, uriVariables);
//        System.out.println(responseBean.toString());
//    }
//参考2
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//class UserControllerTest {
//
//    @Autowired
//    private UserController userController ;
//    @Autowired
//    private WebApplicationContext context;
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup(){
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build;
//    }
//
//    /**
//     * 获取用户列表
//     */
//    @Test(timeOut = 300000)
//    public void testGetUserList() throws Exception {
//        String url = "/user/getUserList";
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//        Assert.assertNotNull(mvcResult);
//    }
//}