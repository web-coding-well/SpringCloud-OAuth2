package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Created by hwj on 2018/9/10.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
//只有开启了这个注解，@PreAuthorize才会起效，否则需要到ResourceServerConfigurerAdapter里配置才行
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()//在浏览器能访问http://localhost:8080/oauth/authorize?client_id=client5&response_type=code&redirect_uri=http://www.baidu.com时必须添加此项，同时在ResourceServerConfigurerAdapter中也必须添加此项
                .and()
                .authorizeRequests().antMatchers("/oauth/**").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }
}
