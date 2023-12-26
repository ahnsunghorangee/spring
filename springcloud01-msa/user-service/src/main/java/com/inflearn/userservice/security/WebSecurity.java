package com.inflearn.userservice.security;

import com.inflearn.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter  {

    private Environment env;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPassworEncoder;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPassworEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPassworEncoder = bCryptPassworEncoder;
    }

    /* 권한 관련 Configure */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        /* 인증이 된 상태에서만 할 수 있는 작업 정의 */
        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/**")
                .access("hasIpAddress('127.0.0.1')")
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), env, userService);
//        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    /* 인증 관련 Configure */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPassworEncoder); // 사용자 검색
    }
}
