package com.inflearn.userservice.controller;

import com.inflearn.userservice.dto.UserDto;
import com.inflearn.userservice.jpa.UserEntity;
import com.inflearn.userservice.service.UserService;
import com.inflearn.userservice.vo.Greeting;
import com.inflearn.userservice.vo.RequestUser;
import com.inflearn.userservice.vo.ResponseUser;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class UserController {
    // application.yml 값을 불러오는 방법 2가지
    private Environment env; // 방법1) Environment

    @Autowired
    private Greeting greeting; // 방법2) @Value

    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService){
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    @Timed(value="users.status", longTask = true)
    public String status(){
        return String.format("It's Working in User Service"
                + ", port(local.server.port)= " + env.getProperty("local.server.port")
                + ", port(server.port)= " + env.getProperty("server.port")
                + ", token secret= " + env.getProperty("token.secret")
                + ", token expiration time= " + env.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    @Timed(value="users.welcome", longTask = true)
    public String welcome(){
        log.info("spring.cloud.client.hostname={}", env.getProperty("spring.cloud.client.hostname"));
        log.info("spring.cloud.client.ip-address={}", env.getProperty("spring.cloud.client.ip-address"));
        return env.getProperty("greeting.message") + " /// " + greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

//        return new ResponseEntity(HttpStatus.CREATED);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
