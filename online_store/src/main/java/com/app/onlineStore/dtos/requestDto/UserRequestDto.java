package com.app.onlineStore.dtos.requestDto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private String email;

    private String password;
    private Double balance;
}
