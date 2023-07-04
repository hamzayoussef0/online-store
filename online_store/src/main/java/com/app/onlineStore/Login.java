package com.app.onlineStore;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class Login {
    private String email;
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
