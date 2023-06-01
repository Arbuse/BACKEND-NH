package com.example.PocektHistory.pocketHistory.user.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegister {
    private String username;
    private String firstName;
    private String lastName;
    private String avatar;
    private String desc;
    private String email;
    private String password;

}
