package com.inflearn.core;

import com.inflearn.core.discount.DiscountPolicy;
import com.inflearn.core.discount.FixDiscountPolicy;
import com.inflearn.core.discount.RateDiscountPolicy;
import com.inflearn.core.member.MemberService;
import com.inflearn.core.member.MemberServiceImpl;
import com.inflearn.core.member.MemoryMemberRepository;
import com.inflearn.core.order.OrderService;
import com.inflearn.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig { // App 전체를 설정 및 구성
    /*
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
    */

    // 위 코드 리팩토링 (중복 제거 및 역할에 따른 구현이 보이도록)
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository()); // 생성자를 통해 객체가 인스턴스를 주입? (= 생성자 주입)
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy(); // OCP 성립
        return new RateDiscountPolicy();
    }
}