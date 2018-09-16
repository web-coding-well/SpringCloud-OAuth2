package com.example.security.config;

import com.example.security.common.AuthExceptionEntryPoint;
import com.example.security.common.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 资源服务器
 * https://blog.csdn.net/u013825231/article/details/80556221
 * Created by hwj on 2018/9/11.
 */
@EnableResourceServer
//我们的springsecurity的拦击是优先于我们的资源拦截的，所以我们需要配置我们的资源中心的拦截要优先于springsecurity，可以通过在类上面加上@Order注解，也可以在配置文件里面加上如下的配置
 //       security.oauth2.resource.filter-order=3
@Order(3)
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
    /**
     * 自定义登录成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler appLoginInSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler appLoginFailureHandler;
    /**
     * 这里对资源的权限配置可以用@EnableGlobalMethodSecurity(prePostEnabled = true)和@PreAuthorize进行代替
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
       /* http
                // Since we want the protected resources to be accessible in the UI as well we need
                // session creation to be allowed (it's disabled by default in 2.0.6)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/public*//**").permitAll()//不需要权限
                .antMatchers("/api*//**").hasAuthority("user")
                .antMatchers("/test*//**").hasAuthority("admin")//只允许拥有admin权限的用户访问
                .and()

                // For some reason we cant just "permitAll" OPTIONS requests which are needed for CORS support. Spring Security
                // will respond with an HTTP 401 nonetheless.
                // So we just put all other requests types under OAuth control and exclude OPTIONS.
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/bata*//**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/bata*//**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/bata*//**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/bata*//**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/bata*//**").access("#oauth2.hasScope('write')")

                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic();*/

        http
                .formLogin()
                //.successHandler(appLoginInSuccessHandler))//如果有必要，在这里可以自定义成功处理器
               // .failureHandler(appLoginFailureHandler)//如果有必要，在这里可以自定义错误处理器
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/open/**").permitAll()//开放的资源不用授权
                .anyRequest().authenticated()//其他任何请求都需要授权
        ;
    }

    private static final String DEMO_RESOURCE_ID = "resource1";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
