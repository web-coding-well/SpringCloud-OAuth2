package com.example.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceApplication.class, args);
	}


	/**
	 * 任何人都需要授权后才能访问
	 * @param value
	 * @return
	 */
	@GetMapping("/test/{value}")
	public String test(@PathVariable String value){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return value;
	}

	/**
	 * 开放资源，任何人都可以访问
	 * @param value
	 * @return
	 */
	@GetMapping("/open/{value}")
	public String publi(@PathVariable String value){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return value;
	}

	/**
	 * 拥有权限res1或者scope2的人才能访问
	 * 权限验证一般细化到具体资源，下面的#oauth2.hasScope不建议使用，每个scope也是有对应的资源列表的，因此在clientDetailsService里声明好客户端scope对应的authorities即可
	 * @param value
	 * @return
	 */
	@GetMapping("/res1/{value}")
	@PreAuthorize("hasAuthority('res1') or  #oauth2.hasScope('scope2')")//@PreAuthorize需要有这个才能生效@EnableGlobalMethodSecurity(prePostEnabled = true)
	public String res1(@PathVariable String value){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return value;
	}

	/**
	 * 拥有权限res2的人才能访问
	 * @param value
	 * @return
	 */
	@GetMapping("/res2/{value}")
	@PreAuthorize("hasAuthority('res2')")
	public String res2(@PathVariable String value){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return value;
	}

	/**
	 * 拥有权限res3的人才能访问
	 * @param value
	 * @return
	 */
	@GetMapping("/res3/{value}")
	@PreAuthorize("hasAuthority('res3')")
	public String res3(@PathVariable String value){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return value;
	}
}
