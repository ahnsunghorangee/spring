package com.inflearn.userservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseOrder { // 주문 내역
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createdAt;

    private String orderId;
}
