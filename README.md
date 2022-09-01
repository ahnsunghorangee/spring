# spring

Study Spring

# IntelliJ IDEA 환경설정

- File > Settings > Build, Execution, Deployment > Gradle > 모두 다 Gradle

- JDK

- 한글 깨짐

# 서블릿

서블릿은 톰캣 같은 웹 애플리케이션 서버를 직접 설치하고, 그 위에 서블릿 코드를 클래스 파일로 빌드해서 올린 다음, 톰캣 서버를 실행하면 된다. 하지만 매우 번거롭다.

스프링 부트는 톰캣 서버를 내장하고 있으므로, 톰캣 서버 설치 없이 편리하게 서블릿 코드를 실행할 수 있다.

## 서블릿 환경 구성

1. 스프링부트를 실행하면 스프링부트가 내장 톰캣 서버를 띄운다.
2. 톰캣서버는 내부에 서블릿 컨테이너 기능이 있어서 서블릿을 생성해준다.
3. http 요청을 웹 브라우저가 우리 서버에 던진다.
4. 서버는 WAS에서 request, response 객체를 만들어서 서블릿을 호출하고 응답을 WAS가 전달해준다.

# HttpServletRequest

- 역할: HTTP 요청 메시지를 개발자가 직접 파싱하기에는 불편해서, Servlet은 요청 메시지를 파싱하고 꺼내서 쓸 수 있는 HttpServletRequest 객체를 제공한다.

- 부가기능 1) 임시 저장소 기능
  - 저장: request.setAttribute(name, value)
  - 조회: request.getAttribute(name)
- 세션 관리 기능
  - request.getSession(create: true)

# 배경지식

war를 해야 JSP를 쓸 수 있다.
