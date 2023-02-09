@ServletComponentScan 

하위 package의 서블릿을 찾아서 자동으로 등록해준다.

서블릿이 호출되면 serivce 메서드가 호출된다

http 요청이 오면 WAS가 Request, Response 객체를 만들어서 Servlet에 던져준다.

웹 브라우저가 http 요청메시지를 만들어서 서버에 던진 것
