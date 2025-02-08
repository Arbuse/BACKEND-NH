package com.example.PocektHistory.pocketHistory.achievements.service;


import com.example.PocektHistory.pocketHistory.achievements.aggregate.AchievementAggregate;
import com.example.PocektHistory.pocketHistory.user.entity.User;
import com.example.PocektHistory.pocketHistory.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class AchievementService {
    private final UserService userService;

    public AchievementService(UserService userService) {
        this.userService = userService;
    }

    public Map<String, String> getUserAchievements(Long userId) throws ExecutionException, InterruptedException {
        User user = userService.getUserById(userId);
        AchievementAggregate achievement = new AchievementAggregate(userId, user.getPoints());
        return achievement.getAchievements();
    }
}
