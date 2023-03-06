package com.inflearn.core.singleton;

import com.inflearn.core.AppConfig;
import com.inflearn.core.member.MemberRepository;
import com.inflearn.core.member.MemberServiceImpl;
import com.inflearn.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {
    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        // 모두 같은 인스턴스를 참고하고 있다.
        System.out.println("memberService -> memberRepository = " + memberService.getMemberRepository());
        System.out.println("orderService -> memberRepository = " + orderService.getMemberRepository());
        System.out.println("memberRepository = " + memberRepository);

        // 모두 같은 인스턴스를 참고하고 있다.
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);


        // call AppConfig.memberService
        // call AppConfig.memberRepository
        // call AppConfig.memberRepository
        // call AppConfig.orderService
        // call AppConfig.memberRepository
        // 이렇게 호출될 줄 알았으나,

        // call AppConfig.memberService
        // call AppConfig.memberRepository
        // call AppConfig.orderService
        // 이렇게 호출된다. >> 왜? @Configuration CGLIB 라이브러리를 이용한 싱글톤 패턴
    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);  // AppConfig도 스프링 빈으로 등록된다.
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean.getClass()); // bean = class com.inflearn.core.AppConfig$$EnhancerBySpringCGLIB$$8152a23c
    }
}
