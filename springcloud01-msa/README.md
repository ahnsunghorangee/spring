# MSA

Study MSA used SpringBoot

- java version: 11
- SpringBoot version: 2.7.x

# 프로젝트 구성

|      서비스       |                역할                | 설명 |
| :---------------: | :--------------------------------: | :--: |
| discovery-service | Spring Cloud Netflix Eureka Server |      |
|   user-service    | Spring Cloud Netflix Eureka Client |      |

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
