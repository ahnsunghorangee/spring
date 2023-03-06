package com.inflearn.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    // private final MemberRepository memberRepository = new MemoryMemberRepository(); // DIP, OCP 위반

    // 인터페이스(추상화)에만 의존 (= DIP 성립)
    private final MemberRepository memberRepository;

    @Autowired // 생성자에 붙여주면 MemberRepository 타입에 맞는 클래스를 찾아와서 자동으로 연결해서 주입해준다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
