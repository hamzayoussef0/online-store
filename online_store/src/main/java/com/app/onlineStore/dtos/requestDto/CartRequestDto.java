package com.app.onlineStore.dtos.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class CartRequestDto {
    private Long UserId;
    private List<Long> productsId;
}
