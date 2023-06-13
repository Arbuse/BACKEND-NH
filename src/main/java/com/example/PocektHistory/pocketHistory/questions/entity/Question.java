package com.example.PocektHistory.pocketHistory.questions.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Question {
    private String tresc_pytania;
    private String poprawna_odpowiedz;
    private String typ;
    private List<String> odpowiedzi;

}
