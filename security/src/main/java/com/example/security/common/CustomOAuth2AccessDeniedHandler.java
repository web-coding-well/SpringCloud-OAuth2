package com.example.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.AbstractOAuth2SecurityExceptionHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwj on 2018/9/16.
 */
@Component
public class CustomOAuth2AccessDeniedHandler extends AbstractOAuth2SecurityExceptionHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;

    public CustomOAuth2AccessDeniedHandler() {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        //this.doHandle(request, response, authException);
        response.setContentType("application/json;charset=UTF-8");
        Map map = new HashMap();
        map.put("code", 405);
        map.put("message", authException.getMessage());
        map.put("data", "");
        map.put("timestamp", String.valueOf(new Date().getTime()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
