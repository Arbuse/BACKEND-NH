package com.example.PocektHistory.pocketHistory.user.models;

import lombok.Data;

@Data
public class UserEditApi {
    private Long userId;
    private String username;
    private String avatar;
    private String desc;
}
