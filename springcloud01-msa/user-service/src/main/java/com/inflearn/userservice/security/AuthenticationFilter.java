package com.inflearn.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflearn.userservice.dto.UserDto;
import com.inflearn.userservice.service.UserService;
import com.inflearn.userservice.vo.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Environment env;
    private UserService userService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, Environment env, UserService userService) {
        super.setAuthenticationManager(authenticationManager);
        this.env = env;
        this.userService = userService;
    }

    /*
        <요청 정보를 보냈을 때 처리해주는 메서드>
        1. 로그인 시도 시, 가장 먼저 실행
        2. 사용자가 던진 정보를 RequestLogin 모양으로 바꿔서 로그인 요청을 한다.
        3. UserServiceImpl.loadUserByUsername() 실행
        4. 성공하면 successfulAuthentication() 실행
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class); // 전달받은 inputStream 을 자바 클래스로 변경

            /* 인증정보를 토큰으로 바꿔서 전달하면 AuthenticationManager()가 id와 password 를 비교해준다.*/
            return getAuthenticationManager().authenticate(
                    // 인증정보 만들기: UsernamePasswordAuthenticationFilter 로 전달 (ID, Password 을 Spring Security 에서 사용할 수 있는 토큰으로 변경이 필요)
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    ) // (ID, Password, 어떤 권한을 가질지)
            );
        } catch(IOException e){
            throw new RuntimeException();
        }
    }

    /* 로그인이 성공했을 때(위 인증처리가 성공했을 때) 어떤 반환값을 줄지 정의 */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(userName);

        String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")) // 암호화
                .compact(); // 토큰 생성

        response.addHeader("token",token);
        response.addHeader("userId",userDetails.getUserId());
    }
}
