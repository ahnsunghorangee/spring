package com.inflearn.userservice.service;

import com.inflearn.userservice.dto.UserDto;
import com.inflearn.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll(); // DB 데이터를 가공할 필요없이 바로 사용하면 DB 객체 모양(UserEntity)으로 바로 받아도 된다.

    UserDto getUserDetailsByEmail(String userName);
}
