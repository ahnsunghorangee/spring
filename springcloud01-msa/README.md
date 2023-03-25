# MSA

Study MSA used SpringBoot

- java version: 11
- SpringBoot version: 2.7.x

# 프로젝트 구성

|      서비스       |                역할                | 설명 |
| :---------------: | :--------------------------------: | :--: |
| discovery-service | Spring Cloud Netflix Eureka Server |      |
|   user-service    | Spring Cloud Netflix Eureka Client |      |

# Spring Cloud Netflix Eureka

역할: 요청정보에 따른 마이크로서비스 위치를 알려준다.

> dependency: Eureks Server

> @SpringBootApplication

- main함수 역할, 스프링부트는 가지고 있는 모든 클래스 중 이 어노테이션을 검색하고 실행
  @EnableEurekaServer: service discovery 서버 등록

# 프로젝트 실행하는 방법

방법1) run 버튼

방법2) Edit Configuration

> VM options에 -Dserver.port=9002 추가 (-D 옵션: 추가하겠다는 의미)

방법3) 인텔리제이 터미널

> mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=9003'

or

> mvn spring-boot:run '-Dspring-boot.run.jvmArguments="-Dserver.port=9003"'

방법4) CMD

> 1. mvn clean (빌드파일 삭제)
> 2. mvn compile package (타겟 폴더, 스냅샷 생성)
> 3. java -jar -Dserver.port=9004 ./target/user-service-0.0.1-SNAPSHOT.jar

방법5) **랜덤포트**

> server.port: 0

- 매번 실행될 때마다 랜덤한 포트로 실행된다. (인스턴스 충돌이 안된다는 장점)

> mvn spring-boot:run

- 포트 설정없이 실행할 수 있다.

> eureka.instance.instance-id: ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}}

- 랜덤포트로 서버를 돌리면 Eureka 서버에 인스턴스가 한 개만 보인다. 랜덤포트 별로 돌아가는 인스턴스를 보기위해 위 설정을 추가한다.

# API Gateway Service

역할: 사용자가 설정한 라우팅 설정에 따라서 각각의 엔드포인트로 클라이언트 대신에서 요청해서 전달해주는 프록시 역할

프론터에서 요청하는 방법이 변경되면 서버를 수정해야되는데 다른 곳에서 사용하는 서비스 요청도 변경해야되는데 이를 조율해주는 역할

- 인증 및 권한 부여
- 서비스 검색 통합
- 응답 캐싱
- 정책, 회로 차단기 및 QoS 다시 시도
- 속도 제한
- 부하 분산
- 로깅, 추적, 상관관계
- 헤더, 쿼리 문자열 및 청구 변환
- IP 허용 목록에 추가

# Netflix Ribbon/Zuul

역할: Spring Cloud에서의 MSA간 통신

- SpringBoot version: 2.3.x 하위 버전에서만 가능

1. RestTemplate
2. Feign Client (인터페이스)

3. Ribbon: Client side(FE에 존재) Load Balancer (비동기x, 최근에 잘 안 쓰임, Spring Cloud Ribbon은 Maintenance(새로운 기능 추가 x) 상태)

   - 서비스 이름으로 호출
   - Health Check
   - 장점: IP/Port로 호출하는 것이 아닌, 마이크로서비스 이름으로 호출

4. Netflix Zuul (최근에 잘 안 쓰임, Spring Cloud Ribbon은 Maintenance(새로운 기능 추가 x) 상태)

   - Routing
   - api gateway 역할

# Spring Cloud Gateway

역할: Zuul 대신 사용 (비동기 o)

apigateway-service를 실행하면 Netty라는 비동기 내장서버가 작동한다. (이전에는 tomcat)

Zuul과 다른점

| 요청URL                                     | first-service Controller          | zuul-service | apigateway-service |
| :------------------------------------------ | :-------------------------------- | :----------: | :----------------: |
| http://localhost:8081/first-service/welcome | @RequestMapping("/")              |      o       |         x          |
| http://localhost:8081/first-service/welcome | @RequestMapping("/first-service") |      x       |         o          |

## Routing 및 Filter 처리하는 방법 2가지

방법1) Java (apigatewayservice/config/FilterConfig.java)

```java
@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) { // applicatio.yml을 Java로 풀이한 것 (같은 역할)
        return builder.routes()
                .route(r -> r.path("/first-service/**") // path가 호출되면
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header") // request header에 적용
                                .addResponseHeader("first-response", "first-response-header") // response header에 적용
                        ) // filter 적용 후
                        .uri("http://localhost:8081") // uri로 이동한다.
                )
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "second-response-header")
                        )
                        .uri("http://localhost:8082")
                )
                .build(); // route에 필요한 내용을 메모리에 반영
    }
}

```

방법2) Property (application.yml)

```yml
spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      routes:
        - id: first-service
          uri: http://localhost:8081/ # 어디로 이동(forwarding)되는지
          predicates: # 조건절 (사용자 요청이 들어오면 위의 uri로 보낸다)
            - Path=/first-service/**
          filters:
            - AddRequestHeader=first-request, first-request-header2
            - AddResponseHeader=first-response, first-response-header2
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
            # 사용자가 요청을 하면 마이크로서비스에는 http://localhost:8082/second-service/** 식으로 요청이 된다.
            # 그래서 second-service 프로젝트에서 @RequestMapping("/second-service")를 추가해주어야 한다.
          filters:
            - AddRequestHeader=second-request, second-request-header2
            - AddResponseHeader=second-response, second-response-header2
```

## Custom Filter

- 사용자 정의로 필터 로그, 인증, locale 바꾸기 등을 적용할 때 사용

- Custom Filter는 Route 각각에게 지정한다.

```yml
spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      routes:
        - id: first-service
          uri: http://localhost:8081/
          predicates:
            - Path=/first-service/**
          filters:
            - CustomFilter
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
          filters:
            - CustomFilter
```

## Global Filter

- 공통 필터

```yml
spring:
  application:
    name: apigateway-service
  cloud:
    gateway:
      default-filters:
        - name: GlobalFilter # GlobalFilter.java
          args:
            baseMessage: Spring Cloud Gateway Global Filter
            preLogger: true
            postLogger: true
      routes:
        - id: first-service
          uri: http://localhost:8081/
          predicates:
            - Path=/first-service/**
          filters:
            - CustomFilter
        - id: second-service
          uri: http://localhost:8082/
          predicates:
            - Path=/second-service/**
          filters:
            - CustomFilter
```
