package com.inflearn.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // 빈으로 등록 (@Service, @Repository 로 사용안할 시)
@Data // lombok: Getter/Setter
@AllArgsConstructor // lombok: 모든 args 를 가진 생성자
@NoArgsConstructor // lombok: args 없는 생성자
public class Greeting {
    @Value("${greeting.message}") // application.yml 값 사용
    private String message;
}
