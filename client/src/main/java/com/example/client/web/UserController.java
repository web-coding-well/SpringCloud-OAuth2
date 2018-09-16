package com.example.client.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hwj on 2018/9/16.
 */
@RestController
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/user/login")
    public String login(@RequestParam String username,@RequestParam String password){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> variable = new LinkedMultiValueMap<>();
        variable.set("username",username);
        variable.set("password",password);
        variable.set("client_id","client1");
        variable.set("client_secret","secret");
        variable.set("grant_type","password");
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(variable,headers);
        return restTemplate.postForEntity("http://localhost:8080/oauth/token",entity,String.class).getBody();
    }






}
