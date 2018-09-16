package com.example.security.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * https://github.com/longfeizheng/security-oauth2
 * https://blog.csdn.net/dandandeshangni/article/details/80472147
 * Created by hwj on 2018/9/16.
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException  extends OAuth2Exception {
    public CustomOauthException(String msg) {
        super(msg);
    }
}
