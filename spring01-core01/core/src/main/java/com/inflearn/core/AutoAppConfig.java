package com.inflearn.core;

import com.inflearn.core.member.MemberRepository;
import com.inflearn.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 시작 위치를 지정 안 하면 package com.inflearn.core; 아래 모든 클래스를 컴포넌트 스캔 대상으로 한다.
        basePackages = "com.inflearn.core",
        basePackageClasses = AutoAppConfig.class,
        // 자동 등록 예외 필터, Configuration 어노테이션이 붙은 클래스는 제외 (중복 등록 방지)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 같은 빈 이름으로 등록(수동 등록 vs 자동 등록) 시 충돌 > spring: 수동 우선, springboot: 충돌 에러 (application.properties에서 Override 설정 가능)
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
