package com.inflearn.core.member;

public class MemberServiceImpl implements MemberService {
    // private final MemberRepository memberRepository = new MemoryMemberRepository(); // DIP, OCP 위반

    // 인터페이스(추상화)에만 의존 (= DIP 성립)
    private final MemberRepository memberRepository;

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
}
