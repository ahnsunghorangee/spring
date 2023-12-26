package com.inflearn.userservice.repository;

import com.inflearn.userservice.jpa.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {  // <데이터베이스랑 연결될 수 있는 Entity 정보, PK 타입>
    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String username);
}
