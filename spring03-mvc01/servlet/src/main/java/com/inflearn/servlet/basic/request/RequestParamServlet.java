package com.inflearn.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. 파라미터 전송 기능
 * 예제1) http://localhost:8080/request-param?username=hello&age=20
 * 예제2) http://localhost:8080/request-param?username=hello&age=20&username=hello2
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 참고) 요청 파라미터를 전부 꺼낼 수 있다.
        Enumeration<String> parameterNames = request.getParameterNames();


        // 예제1) http://localhost:8080/request-param?username=hello&age=20
        System.out.println("[전체 파라미터 조회] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));
        System.out.println("[전체 파라미터 조회] - End");
        System.out.println();


        System.out.println("[단일 파라미터 조회] - start");
        String username = request.getParameter("username");
        String age = request.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println("[단일 파라미터 조회] - end");
        System.out.println();


        // 예제2) http://localhost:8080/request-param?username=hello&age=20&username=hello2
        System.out.println("[이름이 같은 복수 파라미터 조회] - start");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }
        System.out.println("[이름이 같은 복수 파라미터 조회] - end");


        response.getWriter().write("OK");
    }
}
