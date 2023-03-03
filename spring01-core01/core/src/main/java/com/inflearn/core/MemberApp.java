package com.inflearn.core;

import com.inflearn.core.member.Grade;
import com.inflearn.core.member.Member;
import com.inflearn.core.member.MemberService;
import com.inflearn.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        // DIP 위반
        /*
        MemberService memberService = new MemberServiceImpl();
         */

        // DIP 성립
        /*
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        */

        // 위 내용(자바)을 스프링으로 변환
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); // ApplicationContext (= 스프링 컨테이너) + AppConfig.class에 있는 Bean 등록
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // 스프링 컨테이너를 통해 Bean 불러오기

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new Member = " + member.getName());
        System.out.println("findM ember = " + findMember.getName());
    }
}