package com.example.PocektHistory.pocketHistory.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String username;
    private Long userId;
    private String firstName;
    private String lastName;
    private Integer points;
    private String avatar;
    private String desc;
    private Integer games;
    private String email;

    public User(String username, Long userId, String firstName, String lastName, Integer points, String avatar, String desc, Integer games, String email) {
        this.username = username;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
        this.avatar = avatar;
        this.desc = desc;
        this.games = games;
        this.email = email;
    }
}
