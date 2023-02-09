package com.inflearn.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns="/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);

        System.out.println("HelloServlet.service");

        System.out.println("request = " + request);
        System.out.println("response = " + response);


        String username = request.getParameter("username"); // query parameter 불러오기
        System.out.println("username = " + username);

        response.setContentType("text/plain"); // http header에 들어간다.
        response.setCharacterEncoding("utf-8"); // http header에 들어간다.
        response.getWriter().write("hello" + username); // http body에 들어간다.
    }
}
