package com.github.hong0805.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().and().authorizeRequests().antMatchers("/user/sendVerificationCode").permitAll() // 인증 없이 접근 허용
				.antMatchers("/", "/user/login", "/user/register", "/user/**", "/css/**", "/js/**", "/images/**")
				.permitAll() 
				.anyRequest().authenticated() 
				.and().formLogin().loginPage("/user/login").loginProcessingUrl("/user/login")
				.defaultSuccessUrl("/bbs", true) 
				.failureUrl("/user/login?error=true");
	}
}