package com.example.PocektHistory.pocketHistory.achievements.controller;

import com.example.PocektHistory.pocketHistory.achievements.service.AchievementService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping("/getUserAchievements")
    public Map<String, String> getUserAchievements(@RequestParam Long userId) throws ExecutionException, InterruptedException {
        return achievementService.getUserAchievements(userId);
    }
}

