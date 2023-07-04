package com.app.onlineStore.dtos.requestDto;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String name;
    private Double price;
    private Integer quantity;
}
