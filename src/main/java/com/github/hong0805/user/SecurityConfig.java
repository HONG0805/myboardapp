package com.github.hong0805.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().and()
            .authorizeRequests()
                .antMatchers("/user/**").permitAll() 
                .antMatchers("/css/**", "/js/**", "/images/**").permitAll() 
                .antMatchers("/bbs/**").authenticated()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/user/login")
                .usernameParameter("userID")
                .passwordParameter("userPassword")
                .defaultSuccessUrl("/bbs/mainpgae", true)
                .failureUrl("/user/login?error=true")
            .and()
            .logout()
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true);
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}