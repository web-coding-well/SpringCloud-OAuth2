package com.example.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * Created by hwj on 2018/9/10.
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 注意secret需要BCrypt加密，否则会报Encoded password does not look like BCrypt
     *
     * @param s
     * @return
     * @throws ClientRegistrationException
     */
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        //客户端指的是各个子系统，比如有百度贴吧，百度网盘，百度外卖等，它们都可以共用一套用户系统
        //这里的客户端信息实际上应该从数据库获取
        //这里为了演示方便就新建了三个client
        //一是client1，密码模式，scope有app
        //一是client2，密码模式，scope有app
        //一是client3，client模式，scope有app,资源权限有res1
        //一是client4，client模式，scope有app,资源权限有res1，res2

        //请求参数离如果有多个scope则用加号连接如“scope=app+admin”，不传则默认全部scope
        //密码模式
        //post http://ip:port/oauth/token?client_id=**&client_secret=**&grant_type=password&username=**&password=**&scope=**
        //post http://ip:port/oauth/token?client_id=**&client_secret=**&grant_type=refresh_token&scope=**
        // response示例：
        // {
        //"access_token": "6d7bc18f-8179-4b2a-8fe2-af1a92049431",
        //        "token_type": "bearer",
        //        "refresh_token": "23b36405-2b17-4ba2-887e-53698e088215",
        //        "expires_in": 43200,
        //        "scope": "all"
        //}
        //client模式
        //http://ip:port/oauth/token?grant_type=client_credentials&scope=**&client_id=**&client_secret=**
        //response示例：{"access_token":"56465b41-429d-436c-ad8d-613d476ff322","token_type":"bearer","expires_in":25074,"scope":"all"}
        /**
         * client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，客户端有自己的client_id,client_secret对应于用户的username,password，而客户端也拥有自己的authorities，当采取client模式认证时，对应的权限也就是客户端自己的authorities。
         * password模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret。此时，accessToken所包含的权限是用户本身的权限，而不是客户端的权限
         */
        //scope表示此客户端可以授予的权限范围，是authorities的一个集合,且客户端必须得有一个scope
        //第二个参数resourceIds如果设置了，则在资源配置ResourceServerConfigurer中必须配置一致的resourceId
        BaseClientDetails bcd=null;
        if ("client1".equals(s)) {
            bcd = new BaseClientDetails(s, "", "scope", "password,refresh_token", "");//在密码模式scope仍然生效，但authorities不生效，为空即可
            bcd.setClientSecret(passwordEncoder.encode("secret"));
        }
        if ("client2".equals(s)) {
            bcd = new BaseClientDetails(s, "", "scope", "password,refresh_token", "");
            bcd.setClientSecret(passwordEncoder.encode("secret"));
        }
        if ("client3".equals(s)) {
            bcd = new BaseClientDetails(s, "", "scope1", "client_credentials,refresh_token", "res1");//在client模式scope,authorities都生效
            bcd.setClientSecret(passwordEncoder.encode("secret"));
        }
        if ("client4".equals(s)) {
            bcd = new BaseClientDetails(s, "", "scope1,scope2", "client_credentials,refresh_token", "res1,res2");
            bcd.setClientSecret(passwordEncoder.encode("secret"));
        }

        if ("client5".equals(s)) {
            bcd = new BaseClientDetails(s, "", "scope1,scope2", "authorization_code,refresh_token", "res1,res2");
            bcd.setClientSecret(passwordEncoder.encode("secret"));
        }


        return bcd;
    }
}
