package com.example.PocektHistory.pocketHistory.achievements.aggregate;


import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class AchievementAggregate {
    private final Long userId;
    private final int points;
    private final Map<String, String> achievements;

    public AchievementAggregate(Long userId, int points) {
        this.userId = userId;
        this.points = points;
        this.achievements = new HashMap<>();
        assignAchievements();
    }

    private void assignAchievements() {
        if (points >= 100) {
            achievements.put("expert", "Ekspert Historii 🏆");
        }
        if (points >= 50) {
            achievements.put("advanced", "Zaawansowany Historyk 🏅");
        }
        if (points >= 10) {
            achievements.put("beginner", "Początkujący Historyk 🎖");
        }
    }
}
