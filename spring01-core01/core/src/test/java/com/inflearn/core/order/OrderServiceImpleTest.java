package com.inflearn.core.order;

import com.inflearn.core.discount.FixDiscountPolicy;
import com.inflearn.core.member.Grade;
import com.inflearn.core.member.Member;
import com.inflearn.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceImpleTest {

    @Test
    void createOrder(){
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L, "name", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(memoryMemberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(2000);
    }
}
