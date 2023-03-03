# 목차

## spring01-core01

## spring03-mvc01

WAR: Tomcat 서버를 별도로 설치 및 내장 톰캣 모두 가능, JSP 돌리기 위해
jar: springboot안에 포함된 내장 tomcat서버가 작동하면서 스프링부트를 기동
war: application 구조가 web형태로 바뀌어 내장 서버가 없고 외장 서버에 마이크로서비스를 배포해야 한다.

- getParameter는 Query String의 파라미터나 Form의 파라미터 모두 꺼낼 수 있다.

## Dependency

@Configuration: 설정 정보
@Bean: Spring 컨테이너에 등록

## Library

- Jackson: spring 기본 라이브러리, JSON 결과를 파싱해서 자바 객체로 변환하는 라이브러리

## Error

> Cause: invalid source release: 11

- Project Structure -> Project JDK 버전 설정

> 1 error
> role: org.apache.maven.model.validation.ModelValidator
> roleHint: ide

- File > Settings > Build, Execution, Deployment > Build Tools > Maven > Maven home path 설정

## 인텔리제이 단축키

- getter and setter : alt + insert
- override: ctrl + o
- 줄 정리 : ctrl + alt + shift + l
- 한 줄 삭제: ctrl + y
- 한 줄 복사 : ctrl + d
- 한 줄 옮기기 : ctrl + shift + 방향키
- 변수 선언 : ctrl + alt + v
- 세미클론 자동 입력: ctrl + shift + enter
- psvm : main함수 작성
- ctrl + shift + T : 테스트 클래스 생성
- 클래스 실행 : ctrl + shift + F10
- 인터페이스에서 구현체로 이동 : ctrl + alt + B

---

---

---

# 공부해야할 것

> ConcurrentHashMap

- Memory 기반 소스에서 여러 Repository에서 HashMap을 사용하면 동시성 이슈가 일어날 수 있다. 이를 해결하기 위함.

> Static Import
