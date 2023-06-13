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

    //Learning mode
    private Long HP_learningFactor;
    private Long HS_learningFactor;
    private Long NC_learningFactor;
    private Long PA_learningFactor;
    private Long SC_learningFactor;
    private Long SE_learningFactor;

    public User(String username, Long userId, String firstName, String lastName, Integer points, String avatar, String desc, Integer games, String email,
                Long HP_learningFactor, Long HS_learningFactor, Long NC_learningFactor, Long PA_learningFactor, Long SC_learningFactor, Long SE_learningFactor) {
        this.username = username;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
        this.avatar = avatar;
        this.desc = desc;
        this.games = games;
        this.email = email;
        this.HP_learningFactor = HP_learningFactor;
        this.HS_learningFactor = HS_learningFactor;
        this.NC_learningFactor = NC_learningFactor;
        this.PA_learningFactor = PA_learningFactor;
        this.SC_learningFactor = SC_learningFactor;
        this.SE_learningFactor = SE_learningFactor;
    }
}
