package com.app.onlineStore.dtos.responseDto;

import lombok.Data;

import java.util.Map;

@Data
public class CartResponseDto {
    private Long id;
    private String userName ;
    private Map<String,Integer> productDetails;// map with key: nameProduct and value: product quantity used by a specific cart
    private Double totalPrice;
}
