package com.inflearn.core;

import com.inflearn.core.member.Grade;
import com.inflearn.core.member.Member;
import com.inflearn.core.member.MemberService;
import com.inflearn.core.member.MemberServiceImpl;
import com.inflearn.core.order.Order;
import com.inflearn.core.order.OrderService;
import com.inflearn.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
        // DIP 위반
        /*
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();
         */

        // DIP 충족
        /*
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();
         */

        // 위 내용(자바)을 스프링으로 변환
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); // ApplicationContext (= 스프링 컨테이너) + AppConfig.class에 있는 Bean 등록
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // 스프링 컨테이너를 통해 Bean 불러오기
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("order.calculatorPrice = " + order.calculatorPrice());
    }
}