package com.inflearn.core.member;

import com.inflearn.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

//    MemberService memberService = new MemberServiceImpl(); // DIP 위반

    // DIP 성립
    MemberService memberService;

    @BeforeEach // @Test 전에 무조건 실행
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        // given (이런것이 주어졌을 때)
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when (이렇게 했을 때)
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then (이렇게 한다.)
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
