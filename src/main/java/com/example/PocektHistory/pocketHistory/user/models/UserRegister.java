package com.example.PocektHistory.pocketHistory.user.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegister {
    private String username;
    private String email;
    private String password;

}
