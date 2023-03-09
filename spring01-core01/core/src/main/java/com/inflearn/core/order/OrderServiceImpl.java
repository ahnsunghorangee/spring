package com.inflearn.core.order;

import com.inflearn.core.annotation.MainDiscountPolicy;
import com.inflearn.core.discount.DiscountPolicy;
import com.inflearn.core.discount.FixDiscountPolicy;
import com.inflearn.core.discount.RateDiscountPolicy;
import com.inflearn.core.member.Member;
import com.inflearn.core.member.MemberRepository;
import com.inflearn.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // lombok: 필수값(final 붙은 것)을 가지고 생성자를 만들어준다.
public class OrderServiceImpl implements OrderService{
//    private final MemberRepository memberRepository = new MemoryMemberRepository(); // DIP, OCP 위반
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 인터페이스(추상화)에만 의존 (= DIP 성립)
    private final MemberRepository memberRepository; // final: 한번 생성되면 안 바뀐다.
    private final DiscountPolicy discountPolicy;

    /*
    @Autowired // 수정자 주입
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("OrderServiceImpl.setMemberRepository");
        this.memberRepository = memberRepository;
    }

    @Autowired // 수정자 주입
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("OrderServiceImpl.setDiscountPolicy");
        this.discountPolicy = discountPolicy;
    }
    */


    // 롬복 적용 전
    @Autowired // 생성자 1개일 때는 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository,
                            // DiscountPolicy rateDiscountPolicy, // @Autowired 필드명 매칭
//                            @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy,
                            @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("OrderServiceImpl.OrderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice); // 최종 생성된 주문 반환
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
