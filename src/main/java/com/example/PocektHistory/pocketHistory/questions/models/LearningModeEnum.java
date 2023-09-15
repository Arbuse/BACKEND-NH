package com.example.PocektHistory.pocketHistory.questions.models;

public enum LearningModeEnum {
    HP("HP"),
    HS("HS"),
    NC("NC"),
    PA("PA"),
    SC("SC"),
    SE("SE");

    private final String value;

    LearningModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
