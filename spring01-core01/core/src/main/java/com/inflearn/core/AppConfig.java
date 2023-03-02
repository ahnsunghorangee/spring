package com.inflearn.core;

import com.inflearn.core.discount.FixDiscountPolicy;
import com.inflearn.core.member.MemberService;
import com.inflearn.core.member.MemberServiceImpl;
import com.inflearn.core.member.MemoryMemberRepository;
import com.inflearn.core.order.OrderService;
import com.inflearn.core.order.OrderServiceImpl;

public class AppConfig { // App 전체를 설정 및 구성

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); // 생성자를 통해 객체가 인스턴스를 주입? (= 생성자 주입)
    }

    public OrderService orderService(){
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }


}
