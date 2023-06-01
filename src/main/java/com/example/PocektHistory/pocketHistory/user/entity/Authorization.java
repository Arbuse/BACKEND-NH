package com.example.PocektHistory.pocketHistory.user.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Authorization {
    private String password;
    private String email;

    public Authorization(String password, String email, Long userId) {
        this.password = password;
        this.email = email;
        this.userId = userId;
    }

    private Long userId;
}
