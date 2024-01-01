package com.inflearn.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Kafka 기본 정보
 */
@Data
@AllArgsConstructor
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
