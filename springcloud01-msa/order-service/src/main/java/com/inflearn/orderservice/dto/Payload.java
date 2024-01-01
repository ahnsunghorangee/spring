package com.inflearn.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // setter 보다 직관적으로 데이터를 지정할 수 있다.
public class Payload {
    private String order_id;
    private String user_id;
    private String product_id;
    private int qty;
    private int unit_price;
    private int total_price;
}
