package com.example.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 资源服务器
 * https://blog.csdn.net/u013825231/article/details/80556221
 * Created by hwj on 2018/9/11.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
//只有开启了这个注解，@PreAuthorize才会起效，否则需要到ResourceServerConfigurerAdapter里配置才行
@EnableResourceServer
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        tokenService.setClientId("client1");
        tokenService.setClientSecret("secret");
        return tokenService;
    }

    /**
     * 这里对资源的权限配置可以用@EnableGlobalMethodSecurity(prePostEnabled = true)和@PreAuthorize进行代替
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                //  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //.and()
                .authorizeRequests()
                .antMatchers("/open/**").permitAll()//开放的资源不用授权
                .anyRequest().authenticated()//其他任何请求都需要授权
        ;
    }

    private static final String DEMO_RESOURCE_ID = "resource2";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }
}
