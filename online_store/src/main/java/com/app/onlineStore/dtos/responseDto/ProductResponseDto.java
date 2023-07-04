package com.app.onlineStore.dtos.responseDto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDto {

    private Long id;

    private String name;
    private double price;
    private int quantity;

    private List<Long> cartsId; // list of carts ids where the product is located
}
